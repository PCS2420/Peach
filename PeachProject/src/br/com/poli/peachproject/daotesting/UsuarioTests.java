package br.com.poli.peachproject.daotesting;

import static org.junit.Assert.*;

import br.com.poli.peachproject.infrastructure.CursoDAO;
import br.com.poli.peachproject.infrastructure.PersonagemDAO;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.description.Curso;
import br.com.poli.peachproject.model.users.Administrador;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

public class UsuarioTests implements Tests {
	UsuarioDAO uDAO;
	Curso c1;
	@Override
	public void startTests() {
		System.out.println("Populando banco de dados com Personagens e Cursos.");
		PersonagemDAO pDAO = new PersonagemDAO();
		CursoDAO cDAO = new CursoDAO();
		
		Personagem p0 = new Personagem("Goomba", 		"img/char/Goomba.png", 			"img/char/Profile_Goomba.png", -30, -1);
		Personagem p1 = new Personagem("Daisy", 		"img/char/Daisy.png",			"img/char/Profile_Daisy.png", 0, 10);
		Personagem p2 = new Personagem("Koopa", 		"img/char/Koopa.png",			"img/char/Profile_Koopa.png", 11, 20);
		Personagem p3 = new Personagem("Yoshi", 		"img/char/Yoshi.png",			"img/char/Profile_Yoshi.png", 21, 30);
		Personagem p4 = new Personagem("Piranha Plant", "img/char/Piranha Plant.png",	"img/char/Profile_Piranha Plant.png", 31, 40);
		Personagem p5 = new Personagem("Chomp", 		"img/char/Chomp.png",			"img/char/Profile_Chomp.png", 41, 50);
		Personagem p6 = new Personagem("Boo", 			"img/char/Boo.png",				"img/char/Profile_Boo.png", 51, 60);
		Personagem p7 = new Personagem("Toad", 			"img/char/Toad.png",			"img/char/Profile_Toad.png", 61, 70);
		Personagem p8 = new Personagem("Shy Guy", 		"img/char/Shy Guy.png",			"img/char/Profile_Shy Guy.png", 71, 80);
		Personagem p9 = new Personagem("Bowser", 		"img/char/Bowser.png",			"img/char/Profile_Bowser.png", 81, 90);
		Personagem p10 = new Personagem("Peach", 		"img/char/Peach.png",			"img/char/Profile_Peach.png", 91, 500);
		
		pDAO.create(p0);pDAO.create(p1);pDAO.create(p2);pDAO.create(p3);pDAO.create(p4);
		pDAO.create(p5);pDAO.create(p6);pDAO.create(p7);pDAO.create(p8);pDAO.create(p9);pDAO.create(p10);
		
		c1 = new Curso("Engenharia da Computacao");
		Curso c2 = new Curso("Direito Sao Francisco");
		Curso c3 = new Curso("Veterinaria");
		Curso c4 = new Curso("Psicologia");
		Curso c5 = new Curso("Biologia");
		Curso c6 = new Curso("Economia");
		Curso c7 = new Curso("Administracao");
		
		c1.setId(cDAO.create(c1));cDAO.create(c2);cDAO.create(c3);cDAO.create(c4);
		cDAO.create(c5);cDAO.create(c6);cDAO.create(c7);

		System.out.println("Creating UsuarioDAO.");
		uDAO = new UsuarioDAO();
	}
	
	public void test() {
		System.out.println("INSERT Test");
		insert();
		System.out.println("SELECT Test");
		select();
		System.out.println("UPDATE Test");
		update();
		System.out.println("DELETE Test");
		delete();
	}
	
	@Override
	public void insert() { // successful
		Administrador u1 = new Administrador("Administrador No 1", "root", "asdf");
		Administrador u2 = new Administrador("Administrador No 12", "segundo@adm.com", "asdf");
		DescritorPontuavel u3 = new DescritorPontuavel("DescritorP1","7700777", "asdf", 47, false, c1.getId(), getIdPersonagem(47)); // curso 2 = engenharia da computacao
		DescritorPontuavel u4 = new DescritorPontuavel("DescritorP2","cara", "asdf", 50, false, c1.getId(), getIdPersonagem(50));
		RevisorPontuavel u5 = new RevisorPontuavel("RevisorPto1", "7700831", "asdf", 66, false, c1.getId(), getIdPersonagem(66));
		RevisorPontuavel u6 = new RevisorPontuavel("RevisorPto2", "rev", "asdf", 51, false, c1.getId(), getIdPersonagem(51));
		Revisor u7 = new Revisor("RevisorPto1","4542", "asdf", getIdCurso());
		Revisor u8 = new Revisor("DescritorP1","24365", "asdf", getIdCurso());
		
		uDAO.create(u1);
		uDAO.create(u2);
		uDAO.create(u3);
		uDAO.create(u4);
		uDAO.create(u5);
		uDAO.create(u6);
		uDAO.create(u7);
		uDAO.create(u8);
		System.out.println("Criadas 8 entidades no banco de dados! Verificar");
	}

	@Override
	public void select() { // successful
		for (Usuario u : uDAO.retrieveAll()) {
			System.out.println(u);
		}
		
	}


	@Override
	public void update() { // successful
		System.out.println("Realizando update do usuario 7630743. Ele era assim:");
		Usuario u = uDAO.retrieveByLogin("cara");
		System.out.println(u);
		
		u.setId_curso(1);
		u.setNome("Cassio Koji Sakayanagui");
		u.setId_personagem(5);
		u.setPontos(50);
		u.setSenha("novaSenha");
		uDAO.update(u);
		
		Usuario newU = uDAO.retrieveByLogin("7630743");
		System.out.println(newU);
	}

	@Override
	public void delete() { // successful
		Administrador u = new Administrador("ADM DELELTE", "retirar@adm.com", "de-de-de-lete");
		u.setId(uDAO.create(u)); // insere no banco de dados
		
		assertNotNull(uDAO.retrieveByLogin("retirar@adm.com")); // garante que foi inserido o administrador
		uDAO.delete(u); // deleta o usuario
		assertNull(uDAO.retrieveByLogin("retirar@adm.com")); // garante que ele não existe mais
	}
	
	private int getIdPersonagem(int pontos) {
		PersonagemDAO pDAO = new PersonagemDAO();
		return pDAO.retrieveByPoints(pontos).getId();
	}

	private int getIdCurso() {
		CursoDAO cDAO = new CursoDAO();
		for (Curso c : cDAO.retrieveAll()) {
			return c.getId();
		}
		return -1;
	}
}
