package br.com.poli.peachproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.poli.peachproject.infrastructure.PersonagemDAO;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Cadastrar")
public class Cadastrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cadastrar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parametros do formulario
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		int curso = Integer.parseInt(request.getParameter("cursos"));
		String senha = request.getParameter("senha");
		String tipoUsuario = request.getParameter("tipo_usuario");
		
		// Controles
	    HttpSession session = request.getSession();
		UsuarioDAO uDAO = new UsuarioDAO();
		PersonagemDAO pDAO = new PersonagemDAO();

		
		if (tipoUsuario.equals("descritor")) {
			Personagem p = pDAO.retrieveByPoints(0);

			DescritorPontuavel u = new DescritorPontuavel(nome, login, senha, 0, false, curso, p.getId());
			u.setId(uDAO.create(u));
		    session.setAttribute("user", u);
		    session.setAttribute("signupped", true);
			session.setAttribute("personagem", p);
		    
			System.out.println("Usuario criado com sucesso \n" + u);
		    response.sendRedirect("Index");
		    return;
		} else if (tipoUsuario.equals("revisor")) {
			Revisor u = new Revisor(nome, login, senha, curso);
			u.setId(uDAO.create(u));
			session.setAttribute("user", u);
		    session.setAttribute("signupped", true);

			System.out.println("Usuario criado com sucesso \n" + u);
		    response.sendRedirect("Index");
		    return;
		}
		
	    System.out.println("Algo aconteceu de errado no cadastro");
	    response.sendRedirect("Sobre");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parametros do formulario
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String curso = request.getParameter("cursos");
		String senha = request.getParameter("senha");
		String confirmacao = request.getParameter("confirmacao");
		String tipoUsuario = request.getParameter("tipo_usuario");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if ((nome != null && !nome.equals("")) && 
			(login != null && !login.equals("")) &&
			(curso != null && isInteger(curso) && 
			(senha != null && !senha.equals("")) &&
			(confirmacao != null && !confirmacao.equals("")) && 
			(tipoUsuario != null && 
			(tipoUsuario.equals("descritor") || (tipoUsuario.equals("revisor")))))) { // valido
			
			if (!isInteger(login)) {
				response.getWriter().write(new Gson().toJson("login_invalido"));
			} else {
				response.getWriter().write(new Gson().toJson("ok"));
			}
			
		} else {
			response.getWriter().write(new Gson().toJson("campo_vazio"));
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}

