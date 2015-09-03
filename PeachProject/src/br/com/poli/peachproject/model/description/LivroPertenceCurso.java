package br.com.poli.peachproject.model.description;

public class LivroPertenceCurso {
	public static final int ESTADO0_NAOACABADA = 0;
	public static final int ESTADO1_ACABADA = 1;
	
	private int id_livro;
	private int id_curso;
	private int estado;
	
	public LivroPertenceCurso(int id_livro, int id_curso, int estado) {
		this.id_livro = id_livro;
		this.id_curso = id_curso;
		this.estado = estado;
	}
	
	public int getId_livro() {
		return id_livro;
	}
	public void setId_livro(int id_livro) {
		this.id_livro = id_livro;
	}
	public int getId_curso() {
		return id_curso;
	}
	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Livro #" + id_livro + ", Curso #" + id_curso + ", Estado["+estado+"]";
	}
	
}
