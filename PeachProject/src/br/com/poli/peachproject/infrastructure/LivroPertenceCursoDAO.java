package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.LivroPertenceCurso;

public class LivroPertenceCursoDAO {
	
	public int create(LivroPertenceCurso lpc) {
		int doesntapply = -1;
		
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "INSERT INTO livro_pertence_curso (id_livro,id_curso,estado) VALUES (?, ?, ?)";
			PreparedStatement pst = c.prepareStatement(query);
			pst.setInt(1, lpc.getId_livro());
			pst.setInt(2, lpc.getId_curso());
			pst.setInt(3, lpc.getEstado());
			pst.executeUpdate(); // inserido
			MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return doesntapply;
	}
	
	public ArrayList<LivroPertenceCurso> retrieveAll() {
		try {
			Connection c = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM livro_pertence_curso";
		    ResultSet result = c.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<LivroPertenceCurso> ret = new ArrayList<LivroPertenceCurso>();
		    while (result.next()) {
		    	ret.add(new LivroPertenceCurso(result.getInt("id_livro"), result.getInt("id_curso"), 
		    			result.getInt("estado")));
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
	
	public void update(LivroPertenceCurso lpc) {
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "UPDATE livro_pertence_curso SET estado = ? WHERE id_livro = ? AND id_curso = ?";
		    PreparedStatement pst = c.prepareStatement(query);
		    pst.setInt(1, lpc.getEstado());
		    pst.setInt(2, lpc.getId_livro());
		    pst.setInt(3, lpc.getId_curso());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(LivroPertenceCurso lpc) {
		try {
			Connection c = MYSQLConnector.openConnection();
			String query = "DELETE FROM livro_pertence_curso WHERE id_livro = ? AND id_curso = ?";
		    PreparedStatement pst = c.prepareStatement(query);
		    pst.setInt(1, lpc.getId_livro());
		    pst.setInt(2, lpc.getId_curso());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
