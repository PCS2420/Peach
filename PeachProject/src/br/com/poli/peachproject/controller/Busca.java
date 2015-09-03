package br.com.poli.peachproject.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.infrastructure.MYSQLConnector;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Livro;
import br.com.poli.peachproject.model.description.LivroPertenceCurso;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class Busca
 */
@WebServlet("/Busca")
public class Busca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Busca() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String termo = request.getParameter("search");
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		// Gera atributos para cada tipo de revisor no sistema
		if (u instanceof DescritorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			
			request.setAttribute("tipo_usuario", "descritor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof RevisorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			
			request.setAttribute("tipo_usuario", "revisor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof Revisor) {
			
			request.setAttribute("tipo_usuario", "revisor");
			request.setAttribute("login", u.getLogin());
		}
				
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, "
					+ "a.nome as nome_autor, a.id_autor, c.nome as nome_curso "
					+ "FROM livro l, autor a, livro_pertence_curso lpc, curso c "
					+ "WHERE l.id_autor = a.id_autor AND l.id_livro = lpc.id_livro AND lpc.id_curso = c.id_curso "
					+ "AND lpc.estado = ? "
					+ "AND (l.titulo LIKE ? OR a.nome LIKE ?)";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, LivroPertenceCurso.ESTADO0_NAOACABADA);
		    pst.setString(2, "%" + termo + "%" );
		    pst.setString(3, "%" + termo + "%" );
		    ResultSet r = pst.executeQuery();
		    
		    ArrayList<Livro> livros = new ArrayList<Livro>();
		    ArrayList<String> cursos = new ArrayList<String>();
		    while(r.next()) {
		    	cursos.add(r.getString("nome_curso"));
		    	livros.add(new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), r.getString("edicao"), 
		    			r.getInt("ano_publicacao"), r.getInt("id_autor"), r.getString("nome_autor")));
		    }
		    request.setAttribute("livros", livros);
		    request.setAttribute("cursos", cursos);
		    
		    if (!(u instanceof DescritorPontuavel)) {
				query = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, "
						+ "a.nome as nome_autor, a.id_autor, cu.nome as nome_curso "
						+ "FROM descricao d, imagem i, pagina p, capitulo c, livro l, livro_pertence_curso lpc, autor a, curso cu "
						+ "WHERE d.id_imagem = i.id_imagem AND i.id_pagina = p.id_pagina AND p.id_capitulo = c.id_capitulo "
						+ "AND c.id_livro = l.id_livro AND l.id_livro = lpc.id_livro AND l.id_autor = a.id_autor "
						+ "AND lpc.estado = ? AND d.estado <> ? AND d.estado <> ? AND d.id_descritor <> ? "
						+ "AND (l.titulo LIKE ? OR a.nome LIKE ?) "
						+ "GROUP BY l.id_livro";
			    pst = conn.prepareStatement(query);
				pst.setInt(1, LivroPertenceCurso.ESTADO0_NAOACABADA); // buscar livros nao descritos completamente
				pst.setInt(2, Descricao.ESTADO4_DESAPROVADA); // nao listar descricoes recusadas
				pst.setInt(3, Descricao.ESTADO3_APROVADA); // nao listar descricoes aprovadas
				pst.setInt(4, u.getId()); // nao listar descricoes do proprio revisor
			    pst.setString(5, "%" + termo + "%" );
			    pst.setString(6, "%" + termo + "%" );
			    r = pst.executeQuery();
			    
			    ArrayList<Livro> livrosRevisar = new ArrayList<Livro>();
			    ArrayList<String> cursosRevisar = new ArrayList<String>();
			    while(r.next()) {
			    	cursos.add(r.getString("nome_curso"));
			    	livros.add(new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), r.getString("edicao"), 
			    			r.getInt("ano_publicacao"), r.getInt("id_autor"), r.getString("nome_autor")));
			    }
			    
			    request.setAttribute("livrosRevisar", livrosRevisar);
			    request.setAttribute("cursosRevisar", cursosRevisar);
		    }
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/busca.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	

}
