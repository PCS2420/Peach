package br.com.poli.peachproject.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.poli.peachproject.infrastructure.MYSQLConnector;

/**
 * Servlet implementation class Historico
 */
@WebServlet("/Historico")
public class Historico extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Historico() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "SELECT count(id_descricao) FROM mydb.descricao WHERE estado = 1;";
			PreparedStatement pst = c.prepareStatement(query);
			ResultSet latest = pst.executeQuery();
			int descricoes = 0;
	 		while(latest.next()) {
				descricoes = latest.getInt("count(id_descricao)");
			}
	 		request.setAttribute("descPendente", String.valueOf(descricoes));
	 		
	 		query = "SELECT count(id_descricao) FROM mydb.descricao WHERE estado = 3;";
			pst = c.prepareStatement(query);
			latest = pst.executeQuery();
	 		while(latest.next()) {
				descricoes = latest.getInt("count(id_descricao)");
			}
	 		request.setAttribute("descAprovada", String.valueOf(descricoes));

	 		query = "SELECT count(id_descricao) FROM mydb.descricao WHERE estado = 4;";
			pst = c.prepareStatement(query);
			latest = pst.executeQuery();
	 		while(latest.next()) {
				descricoes = latest.getInt("count(id_descricao)");
			}
	 		request.setAttribute("descRejeitada", String.valueOf(descricoes));
	 		
	 		request.getRequestDispatcher("/historico.jsp").forward(request, response);	
	 		MYSQLConnector.closeConnection();
	 	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
