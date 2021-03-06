package br.com.poli.peachproject.model.users;

public class DescritorPontuavel extends Usuario {

	public DescritorPontuavel(int id_usuario, String nome, String nusp, String senha, int pontos, boolean promovido, int id_curso, int id_personagem) {
		super(id_usuario, nome, nusp, senha, pontos, promovido, id_curso, id_personagem);
	}
	
	public DescritorPontuavel(String nome, String nusp, String senha, int pontos, boolean promovido, int id_curso, int id_personagem) {
		super(nome, nusp, senha, pontos, promovido, id_curso, id_personagem);
	}
	
	public RevisorPontuavel promoverParaRevisorPontuavel() {
		return new RevisorPontuavel(id_usuario, nome, login, senha, pontos, promovido, id_curso, id_personagem);
	}
}
