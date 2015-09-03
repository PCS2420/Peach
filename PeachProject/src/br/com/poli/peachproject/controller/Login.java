package br.com.poli.peachproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.infrastructure.PersonagemDAO;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String pass = request.getParameter("senha");
		Usuario u = validateLogin(login, pass);
		
		response.setContentType("application/json"); 
        response.setCharacterEncoding("utf-8"); 
		
		if (u != null) {
		    if (u instanceof DescritorPontuavel) {
		    	u = (DescritorPontuavel) u;
		    } else if (u instanceof RevisorPontuavel) {
		    	u = (RevisorPontuavel) u;
		    } else { // revisor
		    	u = (Revisor) u;
		    }
		    HttpSession session = request.getSession();
		    session.setAttribute("user", u);
		    System.out.println(u);
		    
			PersonagemDAO pDAO = new PersonagemDAO();
			Personagem p = pDAO.retrieveById(u.getId_personagem()); // busca imagem no banco
			session.setAttribute("personagem", p);
		    
		    System.out.println("usuario '" + login + "' logado");
		    session.setAttribute("redirected", true);
		    response.sendRedirect("Index");
            return;
		}
		System.out.println("usuario '" + login + "' nao autorizado");
		request.setAttribute("message", "wrong_pw");
		request.getRequestDispatcher("/sobre.jsp").forward(request, response);
	}
	
	private Usuario validateLogin(String login, String password) {
        // All parameters must be valid
        if (login == null || password == null){
            return null;
        }
		UsuarioDAO uDAO = new UsuarioDAO();
		// Get a user by key
		Usuario u = uDAO.retrieveByLogin(login);
         
        if (u == null){
            return null;
        }
         
        // Check if the password is valid
        if (!u.getSenha().equals(password.trim())){
            return null;
        }
         
        return u;
    }

}
