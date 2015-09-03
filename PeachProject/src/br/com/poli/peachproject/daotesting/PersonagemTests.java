package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.PersonagemDAO;
import br.com.poli.peachproject.model.users.Personagem;

public class PersonagemTests implements Tests {
	PersonagemDAO pDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating PersonagemDAO");
		pDAO = new PersonagemDAO();
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
		Personagem p = new Personagem("Peach", "peach.png", "peachHD.png", 90, 99);
		p.setId(pDAO.create(p));
		System.out.println(p);
	}


	@Override
	public void select() {// successful
		for (Personagem p : pDAO.retrieveAll()) {
			System.out.println(p);
		}
	}

	@Override
	public void update() {// successful
		Personagem p = new Personagem("Peachy", "peaasdchy.png", "peachHD.png", 100, 110);
		p.setId(pDAO.create(p));
		p.setNome("Gooomba");
		pDAO.update(p);
		select();
	}

	@Override
	public void delete() {// successful
		Personagem p = new Personagem("FAJG", "sadasdasd.png", "peachHD.png", 90, 99);
		p.setId(pDAO.create(p));
		
		select();
		pDAO.delete(p);
		select();
	}
	
}
