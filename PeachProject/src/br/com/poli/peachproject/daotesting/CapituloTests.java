package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.CapituloDAO;
import br.com.poli.peachproject.infrastructure.LivroDAO;
import br.com.poli.peachproject.model.description.Capitulo;
import br.com.poli.peachproject.model.description.Livro;

public class CapituloTests implements Tests {
	CapituloDAO chDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating CapituloDAO.");
		chDAO = new CapituloDAO();
	}
	
	@Test
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
		Capitulo ch = new Capitulo(1, "C://grandeGatsby_ch_1_inteiro", getLivroId());
		ch.setId(chDAO.create(ch));
		System.out.println(ch);
	}


	@Override
	public void select() { // successful
		for (Capitulo ch : chDAO.retrieveAll()) {
			System.out.println(ch);
		}
	}

	@Override
	public void update() { // successful
		Capitulo ch = new Capitulo(1, "C://grandeGatsby_ch_1_updateio", getLivroId());
		ch.setId(chDAO.create(ch));
		ch.setPath("CJAISJUFoiah");
		chDAO.update(ch);
		select();
	}

	@Override
	public void delete() { // successful
		Capitulo ch = new Capitulo(1, "C://grandeGatsby_chasd_1_inteiro", getLivroId());
		ch.setId(chDAO.create(ch));
		
		select();
		chDAO.delete(ch);
		select();
	}

	private int getLivroId() {
		LivroDAO lDAO = new LivroDAO();
		for(Livro l : lDAO.retrieveAll()) {
			return l.getId();
		}
		return -1;
	}
}
