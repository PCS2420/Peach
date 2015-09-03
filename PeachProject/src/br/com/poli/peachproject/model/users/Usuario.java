package br.com.poli.peachproject.model.users;

public class Usuario {
	protected int id_usuario;
	protected String nome;
	protected String login; // pode ser nusp
	protected String senha;
	protected int pontos;
	protected boolean promovido;
	protected int id_curso;
	protected int id_personagem;

	// cadastro de usuario descritor // revisor 
	public Usuario(String nome, String login, String senha, int pontos, boolean promovido, int id_curso, int id_personagem) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.pontos = pontos;
		this.promovido = promovido;
		this.id_curso = id_curso;
		this.id_personagem = id_personagem;
	}
	// cadastro de usuario descritor // revisor 
	public Usuario(int id_descritor, String nome, String login, String senha, int pontos, boolean promovido, int id_curso, int id_personagem) {
		this.id_usuario = id_descritor;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.pontos = pontos;
		this.promovido = promovido;
		this.id_curso = id_curso;
		this.id_personagem = id_personagem;
	}
	// cadastro de usuario revisor
	public Usuario(int id_revisor, String nome, String login, String senha, int id_curso) {
		this.id_usuario = id_revisor;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.id_curso = id_curso;
	}
	// cadastro de usuario revisor
	public Usuario(String nome, String login, String senha, int id_curso) { // cadastrto para revisor
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.id_curso = id_curso;
	}
	// cadastro de usuario administrador
	public Usuario(int id_administrador, String nome, String login, String senha) {
		this.id_usuario = id_administrador;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	// cadastro de usuario administrador
	public Usuario(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public int getId() {
		return id_usuario;
	}

	public void setId(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public boolean isPromovido() {
		return promovido;
	}
	
	public void setPromovido(boolean promovido) {
		this.promovido = promovido;
	}
	
	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public int getId_personagem() {
		return id_personagem;
	}

	public void setId_personagem(int id_personagem) {
		this.id_personagem = id_personagem;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "Usuario #" + id_usuario + ", Nome: '"+nome+"', senha: '"+senha + "', login: '" + login
				+"', Ptos["+pontos+"], Curso #" + id_curso + ", Personagem #" + id_personagem + ", Promovido: " + promovido;
	}
	
}
