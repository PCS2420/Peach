package br.com.poli.peachproject.daotesting;

import org.junit.Test;

public class SuperTests {
	@Test
	public void mainTesting() {
		UsuarioTests testingUsers = new UsuarioTests();
		LivroTests testingLivro = new LivroTests();
		ImagemTests testingImagem = new ImagemTests();
		
		testingUsers.startTests();
		testingUsers.test();
		
		testingLivro.startTests();
		testingLivro.test();
		
		testingImagem.startTests();
	}
}
