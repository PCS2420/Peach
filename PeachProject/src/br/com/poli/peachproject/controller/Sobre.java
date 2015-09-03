package br.com.poli.peachproject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.infrastructure.CursoDAO;
import br.com.poli.peachproject.model.description.Curso;

/**
 * Servlet implementation class Sobre
 */
@WebServlet("/Sobre")
public class Sobre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sobre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int logoff = 0;
		if (request.getParameter("logoff") != null) {
			logoff = Integer.parseInt(request.getParameter("logoff"));
		}
		
		HttpSession session = request.getSession();
		
		CursoDAO cDAO = new CursoDAO();
		ArrayList<Curso> cursos = cDAO.retrieveAll();
		request.setAttribute("cursos", cursos);
		
		if (logoff == 1) {
			session.removeAttribute("user");
			System.out.println("Usuario deslogado@Sobre");
		}
		
		if (session.getAttribute("user") == null) {
			request.setAttribute("message", "no_auth");
			request.getRequestDispatcher("/sobre.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/sobre.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
