package br.com.poli.peachproject.model.description;

public class Imagem {
	public final static int ESTADO0_NAODESCRITA = 0;
	public final static int ESTADO1_OCUPADA = 1;
	public final static int ESTADO2_DESCRITA = 2;
	public final static int ESTADO3_REVISADA = 3; 
	
	private  int id_imagem;
	private  int id_pagina;
	private int estado;
	private String path_imagem;
	
	public Imagem(int id_pagina, int estado, String path_imagem) {
		this.id_pagina = id_pagina;
		this.estado = estado;
		this.path_imagem = path_imagem;
	}
	
	public Imagem(int id_imagem, int id_pagina, int estado, String path_imagem) {
		this.id_imagem = id_imagem;
		this.id_pagina = id_pagina;
		this.estado = estado;
		this.path_imagem = path_imagem;
	}
	
	public int getId() {
		return id_imagem;
	}
	public void setId(int id_imagem) {
		this.id_imagem = id_imagem;
	}
	public int getId_pagina() {
		return id_pagina;
	}
	public void setId_pagina(int id_pagina) {
		this.id_pagina = id_pagina;
	}
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getPath_imagem() {
		return path_imagem;
	}
	public void setPath_imagem(String path_imagem) {
		this.path_imagem = path_imagem;
	}
	@Override
	public String toString() {
		return "Imagem #" + id_imagem + ", Pagina # " + id_pagina + ", Estado: ["+estado+"],  Path: [" +path_imagem+ "]";
	}
}
