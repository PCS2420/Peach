package br.com.poli.peachproject.model.users;

public class Personagem {
	private int id_personagem;
	private String nome;
	private String imagem;
	private String imagemHD;
	private int min;
	private int max;
	public Personagem(int id_personagem, String nome, String imagem, String imagemHD, int min, int max) {
		this.id_personagem = id_personagem;
		this.nome = nome;
		this.imagem = imagem;
		this.imagemHD = imagemHD;
		this.min = min;
		this.max = max;
	}
	public Personagem(String nome, String imagem, String imagemHD, int min, int max) {
		this.nome = nome;
		this.imagem = imagem;
		this.imagemHD = imagemHD;
		this.min = min;
		this.max = max;
	}
	public int getId() {
		return id_personagem;
	}
	public void setId(int id_personagem) {
		this.id_personagem = id_personagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public String getImagemHD() {
		return imagemHD;
	}
	public void setImagemHD(String imagemHD) {
		this.imagemHD = imagemHD;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	@Override
	public String toString() {
		return "Personagem #" + id_personagem + ", Nome: '"+nome+"', Imagem: '" + imagem + "', HD: '" + imagemHD + 
				"' [" + min + "," + max+ "]";
	}
}
