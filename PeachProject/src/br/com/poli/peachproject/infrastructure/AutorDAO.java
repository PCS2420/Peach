package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Autor;

public class AutorDAO {
	public int create(Autor a) {
		int id_autor = -1;
		
		try {
		Connection c = MYSQLConnector.openConnection();
		String query = "INSERT INTO autor (nome) VALUES (?)";
		PreparedStatement pst = c.prepareStatement(query);
		pst.setString(1, a.getNome());
		pst.executeUpdate(); // inserido
		
	    // Result set get the result of the SQL query
		String selectID = "SELECT id_autor FROM autor WHERE nome=? ORDER BY id_autor DESC";
	    PreparedStatement select = c.prepareStatement(selectID);
	    	select.setString(1, a.getNome());

	    ResultSet latest = select.executeQuery(); // aqui da erro
	    latest.next();
	    id_autor = latest.getInt("id_autor");
	    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_autor;
	}
	
	public ArrayList<Autor> retrieveAll() {
		try {
			Connection c = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM autor";
		    ResultSet result = c.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Autor> ret = new ArrayList<Autor>();
		    while (result.next()) {
		    	ret.add(new Autor(result.getInt("id_autor"), result.getString("nome")));
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
	
	public void update(Autor a) {
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "UPDATE autor SET nome = ? WHERE id_autor = ?";
		    PreparedStatement pst = c.prepareStatement(query);
		    pst.setString(1, a.getNome());
		    pst.setInt(2, a.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Autor a) {
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "DELETE FROM autor WHERE id_autor = ?";
		    PreparedStatement pst = c.prepareStatement(query);
		    pst.setInt(1, a.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public Autor retrieveAuthorById(int id) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM autor WHERE id_autor = ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id);
		    ResultSet r = pst.executeQuery();
		    while(r.next()) {
		    	return new Autor(r.getInt("id_autor"), r.getString("nome"));
		    }
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Autor retrieveAuthorByName(String term) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM autor WHERE nome LIKE ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setString(1, "%" + term + "%");
		    ResultSet r = pst.executeQuery();
		    while(r.next()) {
		    	return new Autor(r.getInt("id_autor"), r.getString("nome"));
		    }
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Autor> retrieveAuthorsByName(String term) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM autor WHERE nome LIKE ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setString(1, "%" + term + "%");
		    ResultSet r = pst.executeQuery();
		    ArrayList<Autor> aList = new ArrayList<Autor>();
		    while(r.next()) {
		    	aList.add(new Autor(r.getInt("id_autor"), r.getString("nome")));
		    }
		    MYSQLConnector.closeConnection();
		    return aList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
