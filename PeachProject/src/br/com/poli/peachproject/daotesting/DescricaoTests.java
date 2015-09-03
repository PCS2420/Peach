package br.com.poli.peachproject.daotesting;

import org.junit.Before;
import org.junit.Test;

import br.com.poli.peachproject.infrastructure.DescricaoDAO;
import br.com.poli.peachproject.infrastructure.ImagemDAO;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Imagem;
import br.com.poli.peachproject.model.users.Usuario;

public class DescricaoTests implements Tests {
	DescricaoDAO dDAO;
	
	@Before
	@Override
	public void startTests() {
		System.out.println("Creating DescricaoDAO.");
		dDAO = new DescricaoDAO();
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
	public void insert() {
		Descricao d = new Descricao(getIdImagem(), getIdDescritor(), getIdDescritor(), 
									0, "Texto descritivo", "BACKUP", Descricao.getCurrentTimeStamp(), Descricao.getCurrentTimeStamp());
		d.setId(dDAO.create(d));
		System.out.println(d);
	}

	@Override
	public void select() {
		for(Descricao d : dDAO.retrieveAll()) {
			System.out.println(d);
		}
	}

	@Override
	public void update() {
		Descricao d = new Descricao(getIdImagem(), getIdDescritor(), getIdDescritor(), 
				0, "Texto descritivo", "BACKUP", Descricao.getCurrentTimeStamp(), Descricao.getCurrentTimeStamp());
		d.setId(dDAO.create(d));
		d.setTexto("NEW TEXTO");
		dDAO.update(d);
		select();
	}

	@Override
	public void delete() {
		Descricao d = new Descricao(getIdImagem(), getIdDescritor(), getIdDescritor(), 
				0, "Texto descritivo", "BACKUP", Descricao.getCurrentTimeStamp(), Descricao.getCurrentTimeStamp());
		d.setId(dDAO.create(d));
		
		select();
		dDAO.delete(d);
		select();
	}
	
	private int getIdImagem() {
		ImagemDAO iDAO = new ImagemDAO();
		for (Imagem i : iDAO.retrieveAll()) {
			return i.getId();
		}
		return -1;
	}
	
	private int getIdDescritor() {
		UsuarioDAO uDAO = new UsuarioDAO();
		for (Usuario u : uDAO.retrieveAll()) {
			return u.getId();
		}
		return -1;
	}

}