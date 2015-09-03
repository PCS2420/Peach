package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.CursoDAO;
import br.com.poli.peachproject.infrastructure.LivroDAO;
import br.com.poli.peachproject.infrastructure.LivroPertenceCursoDAO;
import br.com.poli.peachproject.model.description.Curso;
import br.com.poli.peachproject.model.description.Livro;
import br.com.poli.peachproject.model.description.LivroPertenceCurso;

public class LivroPertenceCursoTests implements Tests{
	LivroPertenceCursoDAO lpcDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating LivroPertenceCursoDAO.");
		lpcDAO = new LivroPertenceCursoDAO();
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
		LivroPertenceCurso lpc = new LivroPertenceCurso(getIdLivro(), getIdCurso(), 0);
		lpcDAO.create(lpc);
		System.out.println(lpc);
	}

	
	@Override
	public void select() { // successful
		for (LivroPertenceCurso lpc : lpcDAO.retrieveAll()) {
			System.out.println(lpc);
		}
	}
	

	@Override
	public void update() { // successful
		LivroPertenceCurso lpc = getFirstOccurence();
		lpc.setEstado(1);
		lpcDAO.update(lpc);
		select();
	}
	
	@Override
	public void delete() { // successful
		LivroPertenceCurso lpc = getFirstOccurence();

		select();
		lpcDAO.delete(lpc);
		select();
	}
	
	private int getIdLivro() {
		LivroDAO lDAO = new LivroDAO();
		for (Livro l : lDAO.retrieveAll()) {
			return l.getId();
		}
		return -1;
	}
	
	private int getIdCurso() {
		CursoDAO cDAO = new CursoDAO();
		for (Curso c : cDAO.retrieveAll()) {
			return c.getId();
		}
		return -1;
	}
	
	private LivroPertenceCurso getFirstOccurence() {
		for (LivroPertenceCurso lpc : lpcDAO.retrieveAll()) {
			return lpc;
		}
		return null;
	}
}
