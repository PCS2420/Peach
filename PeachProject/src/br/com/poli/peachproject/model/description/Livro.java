package br.com.poli.peachproject.model.description;

public class Livro {
	private int id;
	private String titulo;
	private String editora;
	private String edicao;
	private int ano_publicacao;
	private int id_autor;
	private String autor;
	
	public Livro(String titulo, String editora, String edicao,
			int ano_publicacao, int id_autor) {
		this.titulo = titulo;
		this.editora = editora;
		this.edicao = edicao;
		this.ano_publicacao = ano_publicacao;
		this.id_autor = id_autor;
	}

	public Livro(int id, String titulo, String editora, String edicao,
			int ano_publicacao, int id_autor, String autor) {
		this.id = id;
		this.titulo = titulo;
		this.editora = editora;
		this.edicao = edicao;
		this.ano_publicacao = ano_publicacao;
		this.id_autor = id_autor;
		this.autor = autor;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getEditora() {
		return editora;
	}
	
	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	public String getEdicao() {
		return edicao;
	}
	
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}
	
	public int getAno_publicacao() {
		return ano_publicacao;
	}
	
	public void setAno_publicacao(int ano_publicacao) {
		this.ano_publicacao = ano_publicacao;
	}
	
	public int getId_autor() {
		return id_autor;
	}
	
	public void setId_autor(int id_autor) {
		this.id_autor = id_autor;
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	@Override
	public String toString() {
		return "Livro #" +id +", Titulo: '"+titulo+"', Editora: '"+editora+"', Edicao: "+edicao+", Ano Pub"+ano_publicacao+", Autor #" + id_autor;
	}
}
