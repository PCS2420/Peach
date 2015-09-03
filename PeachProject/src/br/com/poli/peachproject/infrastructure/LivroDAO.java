package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.poli.peachproject.model.description.Livro;

public class LivroDAO {

	public int create(Livro l) {
		int id_livro = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "INSERT INTO livro (titulo,editora,edicao,ano_publicacao,id_autor) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, l.getTitulo());
			pst.setString(2, l.getEditora());
			pst.setString(3, l.getEdicao());
			pst.setInt(4, l.getAno_publicacao());
			pst.setInt(5, l.getId_autor());
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_livro FROM livro WHERE titulo=? ORDER BY id_livro DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, l.getTitulo());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_livro = latest.getInt("id_livro");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_livro;
	}
	
	public ArrayList<Livro> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, a.nome, a.id_autor "
					+ "FROM livro l, autor a WHERE l.id_autor = a.id_autor";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Livro> ret = new ArrayList<Livro>();
		    while (r.next()) {
		    	ret.add(new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), 
		    			r.getString("edicao"), r.getInt("ano_publicacao"), r.getInt("id_autor"), r.getString("nome")));
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
	
	public HashMap<Integer, String> retrieveAllWithAutor() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, a.nome, a.id_autor "
					+ "FROM livro l, autor a WHERE l.id_autor = a.id_autor";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    HashMap<Integer, String> aNameMap = new HashMap<Integer, String>();
		    while (r.next()) {
		    	aNameMap.put(r.getInt("id_livro"), r.getString("nome"));
		    }
		    MYSQLConnector.closeConnection();
		    return aNameMap;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void update(Livro l) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE livro SET titulo = ?, editora = ?, edicao = ?, ano_publicacao = ?, id_autor = ? WHERE id_livro = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setString(1, l.getTitulo());
			pst.setString(2, l.getEditora());
			pst.setString(3, l.getEdicao());
			pst.setInt(4, l.getAno_publicacao());
			pst.setInt(5, l.getId_autor());
		    pst.setInt(6, l.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Livro l) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM livro WHERE id_livro = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, l.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Livro retrieveLivroById(int id_livro) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT l.id_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, a.nome, a.id_autor "
					+ "FROM livro l, autor a WHERE l.id_autor = a.id_autor WHERE id_livro = ?";
		    PreparedStatement pst = conn.prepareStatement(selectID);
		    pst.setInt(1, id_livro);
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) {
		    	return new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), 
		    			r.getString("edicao"), r.getInt("ano_publicacao"), r.getInt("id_autor"), r.getString("nome"));
		    }
		    MYSQLConnector.closeConnection();
		    return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Livro> retrieveLivrosByNome(String nome) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM livro WHERE titulo LIKE ?";
		    PreparedStatement pst = conn.prepareStatement(selectID);
		    pst.setString(1, "%" + nome + "%");
		    ResultSet r = pst.executeQuery();
		    
		    ArrayList<Livro> lLivro = new ArrayList<Livro>();
		    while (r.next()) {
		    	lLivro.add(new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), 
		    			r.getString("edicao"), r.getInt("ano_publicacao"), r.getInt("id_autor"), null));
		    }
		    MYSQLConnector.closeConnection();
		    return lLivro;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Livro retrieveLivroByNome(String nome) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM livro WHERE titulo LIKE ?";
		    PreparedStatement pst = conn.prepareStatement(selectID);
		    pst.setString(1, "%" + nome + "%");
		    ResultSet r = pst.executeQuery();
		    
		    while (r.next()) { // first occurence
		    	return new Livro(r.getInt("id_livro"), r.getString("titulo"), r.getString("editora"), 
		    			r.getString("edicao"), r.getInt("ano_publicacao"), r.getInt("id_autor"), null);
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
