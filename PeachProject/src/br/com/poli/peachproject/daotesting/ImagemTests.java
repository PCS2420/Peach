package br.com.poli.peachproject.daotesting;

import br.com.poli.peachproject.infrastructure.AutorDAO;
import br.com.poli.peachproject.infrastructure.CapituloDAO;
import br.com.poli.peachproject.infrastructure.ImagemDAO;
import br.com.poli.peachproject.infrastructure.LivroDAO;
import br.com.poli.peachproject.infrastructure.PaginaDAO;
import br.com.poli.peachproject.model.description.Autor;
import br.com.poli.peachproject.model.description.Capitulo;
import br.com.poli.peachproject.model.description.Imagem;
import br.com.poli.peachproject.model.description.Livro;
import br.com.poli.peachproject.model.description.Pagina;

public class ImagemTests implements Tests {
	ImagemDAO iDAO;
	
	@Override
	public void startTests() {
		CapituloDAO cDAO = new CapituloDAO();
		PaginaDAO pDAO = new PaginaDAO();
		LivroDAO lDAO = new LivroDAO();
		AutorDAO aDAO = new AutorDAO();
		Autor a = aDAO.retrieveAuthorByName("patterson");
		
		System.out.println("Populando o banco de dados com capitulos");
		Livro l = lDAO.retrieveLivroByNome("Organization");
		Capitulo c1 = new Capitulo(1, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch1/ch1.pdf", l.getId());
		Capitulo c2 = new Capitulo(2, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch2/ch2.pdf", l.getId());
		Capitulo c3 = new Capitulo(3, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/ch3.pdf", l.getId());
		Capitulo c4 = new Capitulo(4, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch4/ch4.pdf", l.getId());
		Capitulo c5 = new Capitulo(5, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch5/ch5.pdf", l.getId());
		Capitulo c6 = new Capitulo(6, "books/" + a.getNome() + "/" + l.getTitulo() + "/ch6/ch6.pdf", l.getId());
		c1.setId(cDAO.create(c1));c2.setId(cDAO.create(c2));c3.setId(cDAO.create(c3));
		c4.setId(cDAO.create(c4));c5.setId(cDAO.create(c5));c6.setId(cDAO.create(c6));
		
		System.out.println("Populando o banco de dados com paginas");
		// Capitulo 1
		Pagina c1p1 = new Pagina(1, c1.getId());
		Pagina c1p2 = new Pagina(2, c1.getId());
		Pagina c1p3 = new Pagina(3, c1.getId());
		Pagina c1p4 = new Pagina(4, c1.getId());
		Pagina c1p5 = new Pagina(5, c1.getId());
		Pagina c1p6 = new Pagina(6, c1.getId());
		Pagina c1p7 = new Pagina(7, c1.getId());
		
		c1p1.setId(pDAO.create(c1p1));
		c1p2.setId(pDAO.create(c1p2));
		c1p3.setId(pDAO.create(c1p3));
		c1p4.setId(pDAO.create(c1p4));
		c1p5.setId(pDAO.create(c1p5));
		c1p6.setId(pDAO.create(c1p6));
		c1p7.setId(pDAO.create(c1p7));
		
		// Capitulo 2
		Pagina c2p1 = new Pagina(1, c2.getId());
		Pagina c2p2 = new Pagina(2, c2.getId());
		Pagina c2p3 = new Pagina(3, c2.getId());
		Pagina c2p4 = new Pagina(4, c2.getId());
		
		c2p1.setId(pDAO.create(c2p1));
		c2p2.setId(pDAO.create(c2p2));
		c2p3.setId(pDAO.create(c2p3));
		c2p4.setId(pDAO.create(c2p4));
		
		// Capitulo 3
		Pagina c3p1 = new Pagina(1, c3.getId());
		Pagina c3p2 = new Pagina(2, c3.getId());
		Pagina c3p3 = new Pagina(3, c3.getId());
		Pagina c3p4 = new Pagina(4, c3.getId());
		Pagina c3p5 = new Pagina(5, c3.getId());
		Pagina c3p6 = new Pagina(6, c3.getId());
		Pagina c3p7 = new Pagina(7, c3.getId());
		Pagina c3p8 = new Pagina(8, c3.getId());
		Pagina c3p9 = new Pagina(9, c3.getId());
		Pagina c3p10 = new Pagina(10, c3.getId());
		Pagina c3p11 = new Pagina(11, c3.getId());
		Pagina c3p12 = new Pagina(12, c3.getId());
		Pagina c3p13 = new Pagina(13, c3.getId());
		Pagina c3p14 = new Pagina(14, c3.getId());
		
		c3p1.setId(pDAO.create(c3p1));
		c3p2.setId(pDAO.create(c3p2));
		c3p3.setId(pDAO.create(c3p3));
		c3p4.setId(pDAO.create(c3p4));
		c3p5.setId(pDAO.create(c3p5));
		c3p6.setId(pDAO.create(c3p6));
		c3p7.setId(pDAO.create(c3p7));
		c3p8.setId(pDAO.create(c3p8));
		c3p9.setId(pDAO.create(c3p9));
		c3p10.setId(pDAO.create(c3p10));
		c3p11.setId(pDAO.create(c3p11));
		c3p12.setId(pDAO.create(c3p12));
		c3p13.setId(pDAO.create(c3p13));
		c3p14.setId(pDAO.create(c3p14));
		
		// Capitulo 4
		Pagina c4p1 = new Pagina(1, c4.getId());
		Pagina c4p2 = new Pagina(2, c4.getId());
		Pagina c4p3 = new Pagina(3, c4.getId());
		Pagina c4p4 = new Pagina(4, c4.getId());
		Pagina c4p5 = new Pagina(5, c4.getId());
		Pagina c4p6 = new Pagina(6, c4.getId());
		Pagina c4p7 = new Pagina(7, c4.getId());
		Pagina c4p8 = new Pagina(8, c4.getId());
		Pagina c4p9 = new Pagina(9, c4.getId());
		Pagina c4p10 = new Pagina(10, c4.getId());
		Pagina c4p11 = new Pagina(11, c4.getId());
		Pagina c4p12 = new Pagina(12, c4.getId());
		Pagina c4p13 = new Pagina(13, c4.getId());
	
		c4p1.setId(pDAO.create(c4p1));
		c4p2.setId(pDAO.create(c4p2));
		c4p3.setId(pDAO.create(c4p3));
		c4p4.setId(pDAO.create(c4p4));
		c4p5.setId(pDAO.create(c4p5));
		c4p6.setId(pDAO.create(c4p6));
		c4p7.setId(pDAO.create(c4p7));
		c4p8.setId(pDAO.create(c4p8));
		c4p9.setId(pDAO.create(c4p9));
		c4p10.setId(pDAO.create(c4p10));
		c4p11.setId(pDAO.create(c4p11));
		c4p12.setId(pDAO.create(c4p12));
		c4p13.setId(pDAO.create(c4p13));
		
		// Capitulo 5
		Pagina c5p1 = new Pagina(1, c5.getId());
		Pagina c5p2 = new Pagina(2, c5.getId());
		
		c5p1.setId(pDAO.create(c5p1));
		c5p2.setId(pDAO.create(c5p2));
		
		// Capitulo 6
		Pagina c6p1 = new Pagina(1, c6.getId());
		Pagina c6p2 = new Pagina(2, c6.getId());
		Pagina c6p3 = new Pagina(3, c6.getId());
		Pagina c6p4 = new Pagina(4, c6.getId());
		
		c6p1.setId(pDAO.create(c6p1));
		c6p2.setId(pDAO.create(c6p2));
		c6p3.setId(pDAO.create(c6p3));
		c6p4.setId(pDAO.create(c6p4));
		
		System.out.println("Creating ImagemDAO.");
		iDAO = new ImagemDAO();
		
		Imagem i_c1p4 = new Imagem(c1p4.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch1/img4.png");
		Imagem i_c2p1 = new Imagem(c2p1.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch2/img1.png");
		Imagem i_c2p3 = new Imagem(c2p3.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch2/img3.png");
		Imagem i_c3p2 = new Imagem(c3p2.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img2.png");
		Imagem i_c3p3 = new Imagem(c3p3.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img3.png");
		Imagem i_c3p6 = new Imagem(c3p6.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img6.png");
		Imagem i_c3p7 = new Imagem(c3p7.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img7.png");
		Imagem i_c3p8 = new Imagem(c3p8.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img8.png");
		Imagem i_c3p10 = new Imagem(c3p10.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch3/img10.png");
		Imagem i_c4p2 = new Imagem(c4p2.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch4/img2.png");
		Imagem i_c5p1 = new Imagem(c5p1.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch5/img1.png");
		Imagem i_c6p2 = new Imagem(c6p2.getId(), Imagem.ESTADO0_NAODESCRITA, 	"books/" + a.getNome() + "/" + l.getTitulo() + "/ch6/img2.png");
		
		System.out.println("INSERT TEST");
		iDAO.create(i_c1p4);
		iDAO.create(i_c2p1);
		iDAO.create(i_c2p3);
		iDAO.create(i_c3p2);
		iDAO.create(i_c3p3);
		iDAO.create(i_c3p6);
		iDAO.create(i_c3p7);
		iDAO.create(i_c3p8);
		iDAO.create(i_c3p10);
		iDAO.create(i_c4p2);
		iDAO.create(i_c5p1);
		iDAO.create(i_c6p2);
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
	public void insert() {
		
	}

	@Override
	public void select() {
		for (Imagem i : iDAO.retrieveAll()) {
			System.out.println(i);
		}
	}

	@Override
	public void update() {
		Imagem i = new Imagem(getIdPagina(), 13, "C://coisa.jpg");
		i.setId(iDAO.create(i));
		i.setPath_imagem("updatePAth");
		iDAO.update(i);
		select();
	}

	@Override
	public void delete() {
		Imagem i = new Imagem(getIdPagina(), 33, "C://todelete.jpg");
		i.setId(iDAO.create(i));
		
		select();
		iDAO.delete(i);
		select();
	}
	
	private int getIdPagina() {
		PaginaDAO pDAO = new PaginaDAO();
		for(Pagina p : pDAO.retrieveAll()) {
			return p.getId();
		}
		return -1;
	}
}
