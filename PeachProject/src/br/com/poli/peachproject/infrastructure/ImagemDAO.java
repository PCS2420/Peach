package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Imagem;

public class ImagemDAO {

	public int create(Imagem i) {
		int id_imagem = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO imagem (id_pagina,estado,path_imagem) VALUES (?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, i.getId_pagina());
			pst.setInt(2, i.getEstado());
			pst.setString(3, i.getPath_imagem());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_imagem FROM imagem WHERE path_imagem=? ORDER BY id_imagem DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, i.getPath_imagem());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_imagem = latest.getInt("id_imagem");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_imagem;
	}
	
	public ArrayList<Imagem> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM imagem";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Imagem> ret = new ArrayList<Imagem>();
		    while (r.next()) {
		    	ret.add(new Imagem(r.getInt("id_imagem"), r.getInt("id_pagina"), r.getInt("estado"), r.getString("path_imagem")));
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
	
	public void update(Imagem i) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE imagem SET id_pagina = ?, estado = ?, path_imagem = ? WHERE id_imagem= ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, i.getId_pagina());
		    pst.setInt(2, i.getEstado());
			pst.setString(3, i.getPath_imagem());
		    pst.setInt(4, i.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Imagem i) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM imagem WHERE id_imagem = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, i.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public Imagem retrieveImagemById(int i1) {
		Imagem i = null;
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "SELECT * FROM imagem WHERE id_imagem = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, i1);
		    ResultSet r = pst.executeQuery(); // aqui da erro
		    while(r.next()) {
		    	i = new Imagem(r.getInt("id_imagem"), r.getInt("id_pagina"), r.getInt("estado"), r.getString("path_imagem"));
		    	return i;
		    }
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return i;
	}
}
