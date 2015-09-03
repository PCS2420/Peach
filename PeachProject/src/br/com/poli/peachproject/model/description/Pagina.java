package br.com.poli.peachproject.model.description;

public class Pagina {
	private int id_pagina;
	private int numero;
	private int id_capitulo;
	
	public Pagina(int numero, int id_capitulo) {
		this.numero = numero;
		this.id_capitulo = id_capitulo;
	}
	
	public Pagina(int id_pagina, int numero, int id_capitulo) {
		this.id_pagina = id_pagina;
		this.numero = numero;
		this.id_capitulo = id_capitulo;
	}
	public int getId() {
		return id_pagina;
	}
	public void setId(int id_pagina) {
		this.id_pagina = id_pagina;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getId_capitulo() {
		return id_capitulo;
	}
	public void setId_capitulo(int id_capitulo) {
		this.id_capitulo = id_capitulo;
	}
	
	@Override
	public String toString() {
		return "Pagina #" + id_pagina + ", No: [" + numero + "], Capitulo #" + id_capitulo;
	}
}
