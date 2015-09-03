package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Curso;

public class CursoDAO {
	public int create(Curso c) {
		int id_curso = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO curso (nome) VALUES (?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, c.getNome());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_curso FROM curso WHERE nome=? ORDER BY id_curso DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, c.getNome());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_curso =  latest.getInt("id_curso");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_curso;
	}
	
	public ArrayList<Curso> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM curso";
		    ResultSet result = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Curso> ret = new ArrayList<Curso>();
		    while (result.next()) {
		    	ret.add(new Curso(result.getInt("id_curso"), result.getString("nome")));
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
	
	public void update(Curso c) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE curso SET nome = ? WHERE id_curso = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setString(1, c.getNome());
		    pst.setInt(2, c.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Curso c) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM curso WHERE id_curso = ?";
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
	
	public Curso retrieveByNome(String nome) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM curso WHERE nome LIKE ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
		    pst.setString(1, "%" + nome + "%");
		    
		    ResultSet result = pst.executeQuery();
		    
		    while (result.next()) {
		    	return new Curso(result.getInt("id_curso"), result.getString("nome"));
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
