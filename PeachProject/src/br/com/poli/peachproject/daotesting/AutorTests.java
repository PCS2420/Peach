package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.AutorDAO;
import br.com.poli.peachproject.model.description.Autor;

public class AutorTests implements Tests {
	AutorDAO aDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating AutorDAO.");
		aDAO = new AutorDAO();
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
		Autor a1 = new Autor("J.K. Rowling");
		Autor a2 = new Autor("T. Dartnall");
		Autor a3 = new Autor("Lois H. Gresh");
		Autor a4 = new Autor("Madnick / Donovan");
		Autor a5 = new Autor("Alan Moore");
		Autor a6 = new Autor("Dan Brown");
		Autor a7 = new Autor("Eiji Yoshikawa");
		Autor a8 = new Autor("Isaac Asimov");
		Autor a9 = new Autor("Eoin Colfer");
		
		aDAO.create(a1);aDAO.create(a2);aDAO.create(a3);aDAO.create(a4);
		aDAO.create(a5);aDAO.create(a6);aDAO.create(a7);aDAO.create(a8);aDAO.create(a9);
		 
	}

	@Override
	public void select() { // successful
		for (Autor a : aDAO.retrieveAll()) {
			System.out.println(a);
		}
	}
	
	@Override
	public void update() { // successful
		Autor aut = new Autor("J.K.J.K.");
		aut.setId(aDAO.create(aut));
		aut.setNome("ResulUPDATE");
		aDAO.update(aut);
		select();
	}
	
	@Override
	public void delete() { // successful
		Autor aut = new Autor("deleteme");
		aut.setId(aDAO.create(aut));
		
		select();
		aDAO.delete(aut);
		select();
	}
	

}
