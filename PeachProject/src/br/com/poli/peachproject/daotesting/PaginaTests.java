package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.CapituloDAO;
import br.com.poli.peachproject.infrastructure.PaginaDAO;
import br.com.poli.peachproject.model.description.Capitulo;
import br.com.poli.peachproject.model.description.Pagina;

public class PaginaTests implements Tests {
	PaginaDAO pDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating PaginaDAO.");
		pDAO = new PaginaDAO();
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
		Pagina p = new Pagina(1, getIdCapitulo());
		p.setId(pDAO.create(p));
		System.out.println(p);
	}

	@Override
	public void select() { // successful
		for (Pagina p :pDAO.retrieveAll()) {
			System.out.println(p);
		}
	}

	@Override
	public void update() { // successful
		Pagina p = new Pagina(124, getIdCapitulo());
		p.setId(pDAO.create(p));
		p.setNumero(333);
		pDAO.update(p);
		select();
	}

	@Override
	public void delete() { // successful
		Pagina p = new Pagina(555, getIdCapitulo());
		p.setId(pDAO.create(p));
		
		select();
		pDAO.delete(p);
		select();
	}

	private int getIdCapitulo() {
		CapituloDAO cDAO = new CapituloDAO();
		for (Capitulo c : cDAO.retrieveAll()) {
			return c.getId();
		}
		return -1;
	}
}
