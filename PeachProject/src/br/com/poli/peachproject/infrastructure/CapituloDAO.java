package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Capitulo;

public class CapituloDAO {

	public int create(Capitulo c) {
		int id_capitulo = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO capitulo (numero,path_capitulo,id_livro) VALUES (?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, c.getNumero());
			pst.setString(2, c.getPath());
			pst.setInt(3, c.getId_livro());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_capitulo FROM capitulo WHERE path_capitulo=? ORDER BY id_capitulo DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, c.getPath());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_capitulo = latest.getInt("id_capitulo");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_capitulo;
	}
	
	public ArrayList<Capitulo> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM capitulo";
		    ResultSet result = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Capitulo> ret = new ArrayList<Capitulo>();
		    while (result.next()) {
		    	ret.add(new Capitulo(result.getInt("id_capitulo"), result.getInt("numero"), result.getString("path_capitulo"), 
		    			result.getInt("id_livro")));
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
	
	public void update(Capitulo c) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE capitulo SET numero=?, path_capitulo=?, id_livro=? WHERE id_capitulo=?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, c.getNumero());
			pst.setString(2, c.getPath());
			pst.setInt(3, c.getId_livro());
		    pst.setInt(4, c.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Capitulo c) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM capitulo WHERE id_capitulo = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, c.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
