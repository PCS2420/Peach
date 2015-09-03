package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.users.Personagem;

public class PersonagemDAO {

	public int create(Personagem p) {
		int id_personagem = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO personagem (nome,path_imagem,path_hd,min,max) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, p.getNome());
		    pst.setString(2, p.getImagem());
		    pst.setString(3, p.getImagemHD());
		    pst.setInt(4, p.getMin());
			pst.setInt(5, p.getMax());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_personagem FROM personagem WHERE path_imagem=? ORDER BY id_personagem DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, p.getImagem());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_personagem = latest.getInt("id_personagem");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_personagem;
	}
	
	public ArrayList<Personagem> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM personagem";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Personagem> ret = new ArrayList<Personagem>();
		    while (r.next()) {
		    	ret.add(new Personagem(r.getInt("id_personagem"), r.getString("nome"), r.getString("path_imagem"), 
		    			r.getString("path_hd"),r.getInt("min"), r.getInt("max")));
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
	
	public void update(Personagem p) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE personagem SET nome = ?, path_imagem = ?, path_hd = ?, min = ?, max = ? "
					+ "WHERE id_personagem = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setString(1, p.getNome());
		    pst.setString(2, p.getImagem());
		    pst.setString(3, p.getImagemHD());
		    pst.setInt(4, p.getMin());
			pst.setInt(5, p.getMax());
			pst.setInt(6, p.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Personagem p) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM personagem WHERE id_personagem = ?";
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

	public Personagem retrieveById(int id_personagem) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM personagem WHERE id_personagem = ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_personagem);
		    ResultSet r = pst.executeQuery();
		    while (r.next()) {
		    	return new Personagem(r.getInt("id_personagem"), r.getString("nome"), r.getString("path_imagem"), 
		    			r.getString("path_hd"), r.getInt("min"), r.getInt("max"));
		    }
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Personagem retrieveByPoints(int points) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM personagem WHERE min <= ? AND max >= ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, points);
			pst.setInt(2, points);
		    ResultSet r = pst.executeQuery();
		    while (r.next()) {
		    	return new Personagem(r.getInt("id_personagem"), r.getString("nome"), r.getString("path_imagem"), 
		    			r.getString("path_hd"), r.getInt("min"), r.getInt("max"));
		    }
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
