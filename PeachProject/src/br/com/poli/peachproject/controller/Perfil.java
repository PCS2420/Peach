package br.com.poli.peachproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.model.users.Administrador;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/Perfil")
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Perfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		// Gera atributos para cada tipo de usuario no sistema
		if (u instanceof DescritorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			request.setAttribute("tipo_usuario", "descritor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("path_hd", p.getImagemHD());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
			request.setAttribute("rank", u.getId_personagem() - 1);
			request.setAttribute("personagem", p.getNome());
		} else if (u instanceof RevisorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");	
			request.setAttribute("tipo_usuario", "revisor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("path_hd", p.getImagemHD());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
			request.setAttribute("rank", u.getId_personagem() - 1);
			request.setAttribute("personagem", p.getNome());
		} else if (u instanceof Revisor) {
			request.setAttribute("tipo_usuario", "revisor");
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof Administrador) {
			// back end
			request.setAttribute("tipo_usuario", "administrador");
		}
		request.setAttribute("nome", u.getNome());
		request.getRequestDispatcher("/perfil.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated constructor stub
	}

}

