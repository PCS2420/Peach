package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.poli.peachproject.model.users.Administrador;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

public class UsuarioDAO {

	public int create(Usuario u) {
		int id_usuario = -1;
		
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query, tipoUsuario = "";
			PreparedStatement pst = null;
			if (u instanceof DescritorPontuavel || u instanceof RevisorPontuavel) {
				query = "INSERT INTO usuario (nome,login,senha,tipo_usuario,pontos,promovido,id_personagem,id_curso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				tipoUsuario = u instanceof DescritorPontuavel ? "descritor_pontuavel" : "revisor_pontuavel";
				pst = conn.prepareStatement(query);
				
			    pst.setInt(5, u.getPontos());
			    pst.setBoolean(6, u.isPromovido());
				pst.setInt(7, u.getId_personagem());
				pst.setInt(8, u.getId_curso());
			} else if (u instanceof Revisor) {
				query = "INSERT INTO usuario (nome,login,senha,tipo_usuario,id_curso) VALUES (?, ?, ?, ?, ?)";
				tipoUsuario = "revisor";
				pst = conn.prepareStatement(query);
				
				pst.setInt(5, u.getId_curso());
			} else if (u instanceof Administrador) {
				query = "INSERT INTO usuario (nome,login,senha,tipo_usuario) VALUES (?, ?, ?, ?)";
				tipoUsuario = "administrador";
				pst = conn.prepareStatement(query);
			} else {
				return -1;
			}
			
			pst.setString(1, u.getNome());
			pst.setString(2, u.getLogin());
		    pst.setString(3, u.getSenha());
			pst.setString(4, tipoUsuario);
			pst.executeUpdate(); // inserido
			
		    // Result set get the result of the SQL query
			String selectID = "SELECT id_usuario FROM usuario WHERE login = ? ORDER BY id_usuario DESC";
		    PreparedStatement select = conn.prepareStatement(selectID);
		    	select.setString(1, u.getLogin());
	
		    ResultSet latest = select.executeQuery(); // aqui da erro
		    latest.next();
		    id_usuario = latest.getInt("id_usuario");
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			System.out.println("[Erro] Erro ao executar query.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Erro] Classe nao encontrada.");
			e.printStackTrace();
		}
		return id_usuario;
	}
	
	public ArrayList<Usuario> retrieveAll() {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM usuario";
		    ResultSet r = conn.prepareStatement(selectID).executeQuery();
		    
		    ArrayList<Usuario> ret = new ArrayList<Usuario>();
		    while (r.next()) {
		    	if (r.getString("tipo_usuario").equals("descritor_pontuavel")) {
		    		ret.add(new DescritorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem")));
		    	} else if (r.getString("tipo_usuario").equals("revisor_pontuavel")) {
		    		ret.add(new RevisorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem")));
		    	} else if (r.getString("tipo_usuario").equals("revisor")) {
		    		ret.add(new Revisor(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha"), r.getInt("id_curso")));	
		    	} else if (r.getString("tipo_usuario").equals("administrador")) {
		    		ret.add(new Administrador(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha")));	
		    	}
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
	
	public void update(Usuario u) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "UPDATE usuario SET nome = ?, login = ?, senha = ?, tipo_usuario = ?, pontos = ?, promovido = ?, id_curso = ? "
					+ ", id_personagem = ? WHERE id_usuario = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setString(1, u.getNome());
		    pst.setString(2, u.getLogin());
		    pst.setString(3, u.getSenha());
		    if (u instanceof DescritorPontuavel)
		    	pst.setString(4, "descritor_pontuavel");
		    else if (u instanceof RevisorPontuavel)
		    	pst.setString(4, "revisor_pontuavel");
		    else if (u instanceof Revisor)
		    	pst.setString(4, "revisor");
		    else if (u instanceof Administrador)
		    	pst.setString(4, "administrador");
		    
		    pst.setInt(5, u.getPontos());
		    pst.setBoolean(6, u.isPromovido());
		    pst.setInt(7, u.getId_curso());
		    pst.setInt(8, u.getId_personagem());
			pst.setInt(9, u.getId());
	 
	      	pst.executeUpdate();
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Usuario u) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String query = "DELETE FROM usuario WHERE id_usuario = ?";
		    PreparedStatement pst = conn.prepareStatement(query);
		    pst.setInt(1, u.getId());
		    pst.executeUpdate();
		    
	      	MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public Usuario retrieveByLogin(String login) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM usuario WHERE login = ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setString(1, login);
		    ResultSet r = pst.executeQuery();
		    while(r.next()) {
		    	if (r.getString("tipo_usuario").equals("descritor_pontuavel")) {
		    		return new DescritorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem"));
		    	} else if (r.getString("tipo_usuario").equals("revisor_pontuavel")) {
		    		return new RevisorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem"));
		    	} else if (r.getString("tipo_usuario").equals("revisor")) {
		    		return new Revisor(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha"), r.getInt("id_curso"));	
		    	} else if (r.getString("tipo_usuario").equals("administrador")) {
		    		return new Administrador(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha"));
		    	}
		    }
		    MYSQLConnector.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario retrieveById(int id_usuario) {
		try {
			Connection conn = MYSQLConnector.openConnection();
			String selectID = "SELECT * FROM usuario WHERE id_usuario = ?";
			PreparedStatement pst = conn.prepareStatement(selectID);
			pst.setInt(1, id_usuario);
		    ResultSet r = pst.executeQuery();
		    while(r.next()) {
		    	if (r.getString("tipo_usuario").equals("descritor_pontuavel")) {
		    		return new DescritorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem"));
		    	} else if (r.getString("tipo_usuario").equals("revisor_pontuavel")) {
		    		return new RevisorPontuavel(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
			    			r.getString("senha"), r.getInt("pontos"), r.getBoolean("promovido"), r.getInt("id_curso"), r.getInt("id_personagem"));
		    	} else if (r.getString("tipo_usuario").equals("revisor")) {
		    		return new Revisor(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha"), r.getInt("id_curso"));	
		    	} else if (r.getString("tipo_usuario").equals("administrador")) {
		    		return new Administrador(r.getInt("id_usuario"), r.getString("nome"), r.getString("login"), 
		    				r.getString("senha"));
		    	}
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
