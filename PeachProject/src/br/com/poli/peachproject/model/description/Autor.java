package br.com.poli.peachproject.model.description;

public class Autor {
	private int id = -1;
	private String nome = null;

	public Autor(String nome) {
		this.nome = nome;
	}
	
	public Autor(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Autor #" + id + ", Nome: '" + nome + "'";
	}
}
