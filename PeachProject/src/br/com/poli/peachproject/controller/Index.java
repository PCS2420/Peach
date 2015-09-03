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

import br.com.poli.peachproject.infrastructure.DescricaoDAO;
import br.com.poli.peachproject.infrastructure.MYSQLConnector;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Livro;
import br.com.poli.peachproject.model.description.LivroPertenceCurso;
import br.com.poli.peachproject.model.users.Administrador;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Atribui atributos caso tenha sido redirecionado
		HttpSession session = request.getSession();
		if (session.getAttribute("redirected") != null && session.getAttribute("redirected").equals(true)) { // veio do Login
			session.setAttribute("redirected", false); // reset attribute
			request.setAttribute("redirected", true);
		} else if (session.getAttribute("finished_description") != null && session.getAttribute("finished_description").equals(true)) { // veio do EnviarDescricao
			session.setAttribute("finished_description", false); // reset attribute
			request.setAttribute("finished_description", true);
		} else if (session.getAttribute("deleted") != null && session.getAttribute("deleted").equals(true)) { // veio do ApagarDescricao
			session.setAttribute("deleted", false);
			request.setAttribute("deleted", true);
		} else if (session.getAttribute("approved_revision") != null && session.getAttribute("approved_revision").equals(true)) { // veio do EnviarRevisao
			session.setAttribute("approved_revision", false);
			request.setAttribute("approved_revision", true);
		} else if (session.getAttribute("disapproved_revision") != null && session.getAttribute("disapproved_revision").equals(true)) { // veio do EnviarRevisao
			session.setAttribute("disapproved_revision", false);
			request.setAttribute("disapproved_revision", true);
		} else if (session.getAttribute("signupped") != null && session.getAttribute("signupped").equals(true)) { // veio do EnviarRevisao
			session.setAttribute("signupped", false);
			request.setAttribute("signupped", true);
		}

		Usuario u = (Usuario) session.getAttribute("user");

		// Verifica se nao houve nenhuma promocao/rebaixamento
		if (u.isPromovido()) { // foi rebaixado
			UsuarioDAO uDAO = new UsuarioDAO();
			//Usuario pontuavel = uDAO.retrieveById(u.getId());
			if (u instanceof DescritorPontuavel){ // foi rebaixado
				request.setAttribute("user_downgraded", true);
			}
			else if (u instanceof RevisorPontuavel){ //foi promovido
				request.setAttribute("user_upgraded", true);	
			}
			u.setPromovido(false);
			uDAO.update(u); // atualiza usuario no banco de dados
			session.setAttribute("user", u); // atualiza usuario na session
		}
		
		// Verifica se nao existe descricao inacabada OU revisao inacabada
		DescricaoDAO dDAO = new DescricaoDAO();
		Descricao inacabada = dDAO.retrieveByDescritorAndState(u.getId(), Descricao.ESTADO0_NAOACABADA); // descricao 
		Descricao inacabada2 = dDAO.retrieveByRevisorAndState(u.getId(), Descricao.ESTADO2_OCUPADA_POR_REVISOR); // revisao
		if (inacabada != null) { // nao precisa testar se o usuario existe pois a request seria trapada no Filter
			System.out.println("Existe uma descricao inacabada!");
			
			request.setAttribute("unfinished_description", true);
			request.setAttribute("id_imagem", inacabada.getId_imagem());
		} else if ((u instanceof Revisor || u instanceof RevisorPontuavel) && inacabada2 != null){
			System.out.println("Existe uma revisao inacabada!");
			
			request.setAttribute("unfinished_revision", true);
			request.setAttribute("id_imagem", inacabada2.getId_imagem());
			request.setAttribute("id_descricao", inacabada2.getId());
		}
		
		// Gera atributos para cada tipo de revisor no sistema
		if (u instanceof DescritorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			
			request.setAttribute("livros", getLivrosForUsuarioDescritor(u));
			request.setAttribute("tipo_usuario", "descritor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof RevisorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			
			request.setAttribute("livrosRevisor", getLivrosForUsuarioRevisor(u));
			request.setAttribute("livros", getLivrosForUsuarioDescritor(u));
			request.setAttribute("tipo_usuario", "revisor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof Revisor) {
			
			request.setAttribute("livrosRevisor", getLivrosForUsuarioRevisor(u));
			request.setAttribute("livros", getLivrosForUsuarioDescritor(u));
			request.setAttribute("tipo_usuario", "revisor");
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof Administrador) {
			// back end
			request.setAttribute("tipo_usuario", "administrador");
		}
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private ArrayList<Livro> getLivrosForUsuarioDescritor(Usuario u) {
		// Seleciona livros a partir do curso
		ArrayList<Livro> list = null;
		try {
			Connection c = MYSQLConnector.openConnection(); // nao pode ficar exatamente em livroDAO
			String query = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, l.id_autor, a.nome "
					+ "FROM livro l, livro_pertence_curso lpc, curso c, autor a "
					+ "WHERE l.id_livro = lpc.id_livro AND c.id_curso = lpc.id_curso AND a.id_autor = l.id_autor AND "
					+ "c.id_curso = ? AND lpc.estado = ?;";
			PreparedStatement pst = c.prepareStatement(query);
			pst.setInt(1, u.getId_curso());
			pst.setInt(2, LivroPertenceCurso.ESTADO0_NAOACABADA); // vale para estado de lpc tambem
			ResultSet res = pst.executeQuery();
			
			list = new ArrayList<Livro>();
			while(res.next()) {
				list.add(new Livro(res.getInt("id_livro"), res.getString("titulo"), res.getString("editora"), 
		    			res.getString("edicao"), res.getInt("ano_publicacao"), res.getInt("id_autor"), res.getString("nome")));
			}
		} catch (SQLException e)  {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private ArrayList<Livro> getLivrosForUsuarioRevisor(Usuario u) {
		// Seleciona livros a partir do curso
		ArrayList<Livro> list = null;
		try {
			Connection c = MYSQLConnector.openConnection(); // nao pode ficar exatamente em livroDAO
			String query = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, l.id_autor, a.nome "
					+ "FROM descricao d, imagem i, pagina p, capitulo c, livro l, livro_pertence_curso lpc, autor a "
					+ "WHERE d.id_imagem = i.id_imagem AND i.id_pagina = p.id_pagina AND p.id_capitulo = c.id_capitulo "
					+ "AND c.id_livro = l.id_livro AND l.id_livro = lpc.id_livro AND l.id_autor = a.id_autor "
					+ "AND lpc.id_curso = ? AND lpc.estado = ? AND d.estado <> ? AND d.estado <> ? AND d.id_descritor <> ? "
					+ "GROUP BY l.id_livro";
			PreparedStatement pst = c.prepareStatement(query);
			pst.setInt(1, u.getId_curso());
			pst.setInt(2, LivroPertenceCurso.ESTADO0_NAOACABADA); // buscar livros nao descritos completamente
			pst.setInt(3, Descricao.ESTADO4_DESAPROVADA); // nao listar descricoes recusadas
			pst.setInt(4, Descricao.ESTADO3_APROVADA); // nao listar descricoes aprovadas
			pst.setInt(5, u.getId()); // nao listar descricoes do proprio revisor
			ResultSet res = pst.executeQuery();
			
			list = new ArrayList<Livro>();
			while(res.next()) {
				Livro l = new Livro(res.getInt("id_livro"), res.getString("titulo"), res.getString("editora"), 
		    			res.getString("edicao"), res.getInt("ano_publicacao"), res.getInt("id_autor"), res.getString("nome"));
				list.add(l);
			}
		} catch (SQLException e)  {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
}
