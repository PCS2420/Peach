package br.com.poli.peachproject.model.users;

public class Administrador extends Usuario {	
	public Administrador(int id_usuario, String nome, String login, String senha) {
		super(id_usuario, nome, login, senha);
	}
	public Administrador(String nome, String login, String senha) {
		super(nome, login, senha);
	}
	@Override
	public String toString() {
		return "ID: [" + id_usuario + "] Nome: '" + nome + "', Login: '" + login + "', Senha: '" + senha + "'" ;
	}
} 
