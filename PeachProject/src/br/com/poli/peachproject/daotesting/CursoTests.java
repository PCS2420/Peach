package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.CursoDAO;
import br.com.poli.peachproject.model.description.Curso;

public class CursoTests implements Tests {
	CursoDAO cDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating CursoDAO");
		cDAO = new CursoDAO();
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
	public void insert() { //successful
		Curso c = new Curso("Engenharia Quimica");
		c.setId(cDAO.create(c));
		System.out.println(c);
	}
	
	@Override
	public void select() { //successful
		for (Curso c : cDAO.retrieveAll()) {
			System.out.println(c);
		}
	}
	
	@Override
	public void update() { //successful
		Curso c = new Curso("Engenharia Seria");
		c.setId(cDAO.create(c));
		c.setNome("Engenharia Eletrica");
		cDAO.update(c);
		select();
	}

	@Override
	public void delete() { //successful
		Curso c = new Curso("Medicina");
		c.setId(cDAO.create(c));
		select();
		cDAO.delete(c);
		select();
	}

}
