package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Pagina;

public class PaginaDAO {

	public int create(Pagina p) {
		int id_pagina = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO pagina (numero,id_capitulo) VALUES (?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, p.getNumero());
			pst.setInt(2, p.getId_capitulo());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_pagina FROM pagina WHERE numero=? AND id_capitulo=? ORDER BY id_pagina DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setInt(1, p.getNumero());
		    	select.setInt(2, p.getId_capitulo());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_pagina = latest.getInt("id_pagina");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_pagina;
	}
	
	public ArrayList<Pagina> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM pagina";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Pagina> ret = new ArrayList<Pagina>();
		    while (r.next()) {
		    	ret.add(new Pagina(r.getInt("id_pagina"), r.getInt("numero"), r.getInt("id_capitulo")));
		    }
		    MYSQLConnector.closeConnection();
		    return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(Pagina p) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE pagina SET numero = ?, id_capitulo = ? WHERE id_pagina= ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, p.getNumero());
			pst.setInt(2, p.getId_capitulo());
			pst.setInt(3, p.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Pagina p) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM pagina WHERE id_pagina = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, p.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
