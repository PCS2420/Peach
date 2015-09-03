package br.com.poli.peachproject.model.description;

public class Capitulo {
	private int id_capitulo;
	private int numero;
	private String path;
	private int id_livro;
	
	public Capitulo(int numero, String path, int id_livro) {
		this.numero = numero;
		this.path = path;
		this.id_livro = id_livro;
	}
	public Capitulo(int id_capitulo, int numero, String path, int id_livro) {
		this.id_capitulo = id_capitulo;
		this.numero = numero;
		this.path = path;
		this.id_livro = id_livro;
	}
	public int getId() {
		return id_capitulo;
	}
	public void setId(int id_capitulo) {
		this.id_capitulo = id_capitulo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getId_livro() {
		return id_livro;
	}
	public void setId_livro(int id_livro) {
		this.id_livro = id_livro;
	}
	
	@Override
	public String toString() {
		return "Capitulo #" + id_capitulo + ", No:["+ numero +"], Path: ["+ path +"], Livro #" + id_livro;
	}
}
