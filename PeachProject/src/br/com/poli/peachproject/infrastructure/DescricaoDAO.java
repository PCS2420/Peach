package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.description.Descricao;

public class DescricaoDAO {

	public int create(Descricao d) {
		int id_descricao = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO descricao "
					+ "(id_imagem, id_descritor, estado, texto, texto_backup, ultima_mod_texto, ultima_mod_backup)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)"; // ao criar descricao ainda nao tem revisor definido
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, d.getId_imagem());
			pst.setInt(2, d.getId_descritor());
			pst.setInt(3, d.getEstado());
			pst.setString(4, d.getTexto());
			pst.setString(5, d.getTexto_backup());
			pst.setTimestamp(6, d.getUltima_mod_texto());
			pst.setTimestamp(7, d.getUltima_mod_backup());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_descricao FROM descricao WHERE id_imagem = ? AND id_descritor = ? ORDER BY id_descricao DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setInt(1, d.getId_imagem());
		    	select.setInt(2, d.getId_descritor());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_descricao = latest.getInt("id_descricao");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_descricao;
	}
	
	public ArrayList<Descricao> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Descricao> ret = new ArrayList<Descricao>();
		    while (r.next()) {
		    	ret.add(new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup")));
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

	public void updateWithoutRevisor(Descricao d) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE descricao SET id_imagem = ?, id_descritor = ?, estado = ?, "
					+ "texto = ?, texto_backup = ?, ultima_mod_texto = ?, ultima_mod_backup = ? WHERE id_descricao = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, d.getId_imagem());
			pst.setInt(2, d.getId_descritor());
			pst.setInt(3, d.getEstado());
			pst.setString(4, d.getTexto());
			pst.setString(5, d.getTexto_backup());
			pst.setTimestamp(6, d.getUltima_mod_texto());
			pst.setTimestamp(7, d.getUltima_mod_backup());
			pst.setInt(8, d.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Descricao d) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE descricao SET id_imagem = ?, id_descritor = ?, id_revisor = ?, estado = ?, "
					+ "texto = ?, texto_backup = ?, ultima_mod_texto = ?, ultima_mod_backup = ? WHERE id_descricao = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, d.getId_imagem());
			pst.setInt(2, d.getId_descritor());
			pst.setInt(3, d.getId_revisor());
			pst.setInt(4, d.getEstado());
			pst.setString(5, d.getTexto());
			pst.setString(6, d.getTexto_backup());
			pst.setTimestamp(7, d.getUltima_mod_texto());
			pst.setTimestamp(8, d.getUltima_mod_backup());
		    pst.setInt(9, d.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Descricao d) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM descricao WHERE id_descricao = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, d.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Descricao retrieveByImageUserAndState(int id_imagem, int id_descritor, int estado) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao WHERE id_imagem = ? AND id_descritor = ? AND estado = ? ORDER BY id_descricao DESC";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_imagem);
			pst.setInt(2, id_descritor);
			pst.setInt(3, estado);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Descricao retrieveByDescritorAndState(int id_descritor, int estado) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao WHERE id_descritor = ? AND estado = ? ORDER BY id_descricao DESC";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_descritor);
			pst.setInt(2, estado);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Descricao retrieveByRevisorAndState(int id_revisor, int estado) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao WHERE id_revisor = ? AND estado = ? ORDER BY id_descricao DESC";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_revisor);
			pst.setInt(2, estado);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Descricao retrieveById(int id_descricao) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao WHERE id_descricao = ? ORDER BY id_descricao DESC";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_descricao);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Descricao retrieveByIdImagem(int id_imagem) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM descricao WHERE id_imagem = ? ORDER BY id_descricao DESC";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_imagem);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"), 
		    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"), 
		    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
