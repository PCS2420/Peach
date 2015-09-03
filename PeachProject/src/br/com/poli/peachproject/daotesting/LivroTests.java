package br.com.poli.peachproject.daotesting;


import br.com.poli.peachproject.infrastructure.AutorDAO;
import br.com.poli.peachproject.infrastructure.CursoDAO;
import br.com.poli.peachproject.infrastructure.LivroDAO;
import br.com.poli.peachproject.infrastructure.LivroPertenceCursoDAO;
import br.com.poli.peachproject.model.description.Autor;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Livro;
import br.com.poli.peachproject.model.description.LivroPertenceCurso;

public class LivroTests implements Tests {
	LivroDAO lDAO;
	AutorDAO aDAO;
	Autor a1, a2, a3, a4, a5, a6, a7, a8, a9, a0;
	@Override
	public void startTests() {
		System.out.println("Populating authors");
		aDAO = new AutorDAO();
		
		// Shoudnt allow special characters in names
		a1 = new Autor("J.K. Rowling"); // Harry Potter
		a2 = new Autor("T. Dartnall"); // Artificial Intelligence
		a3 = new Autor("Christopher Alexander"); // Timeless Way of Building
		a4 = new Autor("Madnick Donovan"); // Operating Systems
		a5 = new Autor("Alan Moore"); // Watchman
		a6 = new Autor("Dan Brown"); // Angels and Demons
		a7 = new Autor("Eiji Yoshikawa"); // Musashi
		a8 = new Autor("Isaac Asimov"); // Foundation
		a9 = new Autor("Eoin Colfer"); // Artemis Fowl
		a0 = new Autor("David A. Patterson"); // Computer Organization and Design
		
		a1.setId(aDAO.create(a1));
		a2.setId(aDAO.create(a2));
		a3.setId(aDAO.create(a3));
		a4.setId(aDAO.create(a4));
		a5.setId(aDAO.create(a5));
		a6.setId(aDAO.create(a6));
		a7.setId(aDAO.create(a7));
		a8.setId(aDAO.create(a8));
		a9.setId(aDAO.create(a9));
		a0.setId(aDAO.create(a0));
		
		System.out.println("Creating LivroDAO.");
		lDAO = new LivroDAO();
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
		Livro l1 = new Livro("Harry Potter and the Sorcere's Stone", "Bloomsbury Publishing plc", "5", 1997, a1.getId());
		Livro l2 = new Livro("Computer Organization and Design", "Morgan Kauphann", "4", 2000, a0.getId());
		Livro l3 = new Livro("Timeless Way of Building", "Oxford University Press", "3", 2000, a3.getId());
		Livro l4 = new Livro("Operating Systems", "Mcgraw-Hill College", "5", 2000, a4.getId());
		Livro l5 = new Livro("Watchman", "EDIT THIS PLEASE", "3", 2000, a5.getId()); // DC Comics
		Livro l6 = new Livro("Angels and Demons", "Pocket Books", "2", 2000, a6.getId());
		Livro l7 = new Livro("Musashi", "Asahi Shinbun", "1", 2000, a7.getId());
		Livro l8 = new Livro("Foundation", "Gnome Press", "1", 1951, a8.getId());
		Livro l9 = new Livro("Artemis Fowl", "Puffin Books", "1", 2001, a9.getId());
		Livro l0 = new Livro("DELETE", "DELETE", "1", 2001, a9.getId());
		
		l1.setId(lDAO.create(l1));
		l2.setId(lDAO.create(l2));
		l3.setId(lDAO.create(l3));
		l4.setId(lDAO.create(l4));
		l5.setId(lDAO.create(l5));
		l6.setId(lDAO.create(l6));
		l7.setId(lDAO.create(l7));
		l8.setId(lDAO.create(l8));
		l9.setId(lDAO.create(l9));
		l0.setId(lDAO.create(l0));
		
		CursoDAO cDAO = new CursoDAO();
		int id_curso = cDAO.retrieveByNome("Engenharia da").getId();
		LivroPertenceCursoDAO lpcDAO = new LivroPertenceCursoDAO();
		lpcDAO.create(new LivroPertenceCurso(l1.getId(), id_curso, Descricao.ESTADO0_NAOACABADA));
		lpcDAO.create(new LivroPertenceCurso(l2.getId(), id_curso, Descricao.ESTADO0_NAOACABADA));
		lpcDAO.create(new LivroPertenceCurso(l3.getId(), id_curso, Descricao.ESTADO0_NAOACABADA));
		lpcDAO.create(new LivroPertenceCurso(l4.getId(), id_curso, Descricao.ESTADO0_NAOACABADA));
		lpcDAO.create(new LivroPertenceCurso(l5.getId(), id_curso, Descricao.ESTADO0_NAOACABADA));
	}

	@Override
	public void select() { // successful
		for (Livro l : lDAO.retrieveAll()) {
			System.out.println(l);
		}
	}

	@Override
	public void update() { // successful
		Livro toUpdate = lDAO.retrieveLivrosByNome("Watchman").get(0); // supondo que houve insert
		toUpdate.setEditora("DC Comics");
		lDAO.update(toUpdate);
		System.out.println(lDAO.retrieveLivrosByNome("Watchman").get(0));
	}

	@Override
	public void delete() { // successful
		Livro lDelete = lDAO.retrieveLivrosByNome("DELETE").get(0);
		lDAO.delete(lDelete);
		select();
	}
	
}
