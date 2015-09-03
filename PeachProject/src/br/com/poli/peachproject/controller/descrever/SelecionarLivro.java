package br.com.poli.peachproject.controller.descrever;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.poli.peachproject.infrastructure.ImagemDAO;
import br.com.poli.peachproject.infrastructure.MYSQLConnector;
import br.com.poli.peachproject.model.description.Imagem;

/**
 * Servlet implementation class SelecionarLivro
 */
@WebServlet("/SelecionarLivro")
public class SelecionarLivro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelecionarLivro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_livro = Integer.parseInt(request.getParameter("id_livro"));
		
		try {
		Connection c = MYSQLConnector.openConnection();
		String query = "SELECT imagem.id_imagem FROM capitulo, pagina, imagem "
				+ "WHERE id_livro = ? AND capitulo.id_capitulo = pagina.id_capitulo "
				+ "AND pagina.id_pagina = imagem.id_pagina AND imagem.estado = ?;";
		PreparedStatement pst = c.prepareStatement(query);
		pst.setInt(1, id_livro);
		pst.setInt(2, Imagem.ESTADO0_NAODESCRITA);
		ResultSet latest = pst.executeQuery();
		
		ArrayList<Integer> imagens = new ArrayList<Integer>();
 		while(latest.next()) {
			imagens.add(latest.getInt("id_imagem"));
		}
 		if (imagens.size() == 0) {
 			System.out.println("Nao tem imagens exception");
 		} else {
	 		int id_im1, id_im2;
	 		int i1 = randInt(0, imagens.size() - 1);
	 		id_im1 = imagens.get(i1);
	 		imagens.remove(i1);
	 		if (imagens.size() > 0) {
	 			i1 = randInt(0, imagens.size() - 1);
	 			id_im2 = imagens.get(i1);
	 		} else {
	 			id_im2 = id_im1;
	 		}
	 		
	 		ImagemDAO iDAO = new ImagemDAO();
	 		Imagem im1 = iDAO.retrieveImagemById(id_im1);
	 		Imagem im2 = iDAO.retrieveImagemById(id_im2);
	 		
	 		request.setAttribute("id_livro", id_livro);
			request.setAttribute("im1", im1);
			request.setAttribute("im2", im2);
			request.getRequestDispatcher("/escolher_imagem.jsp").forward(request, response);
 		}
 		MYSQLConnector.closeConnection();
		} catch (SQLException e)  {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
