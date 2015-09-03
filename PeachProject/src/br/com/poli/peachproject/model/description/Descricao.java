package br.com.poli.peachproject.model.description;

import java.sql.Timestamp;

public class Descricao {
	public static final int ESTADO0_NAOACABADA = 0;
	public static final int ESTADO1_ACABADA = 1;
	public static final int ESTADO2_OCUPADA_POR_REVISOR = 2;
	public static final int ESTADO3_APROVADA = 3;
	public static final int ESTADO4_DESAPROVADA = 4;
	
	private int id_descricao;
	private int id_imagem;
	private int id_descritor;
	private int id_revisor;
	private int estado;
	private String texto;
	private String texto_backup;
	private Timestamp ultima_mod_texto;
	private Timestamp ultima_mod_backup;
	
	public Descricao(int id_imagem, int id_descritor,
			int id_revisor, int estado, String texto, String texto_backup,
			Timestamp ultima_mod_texto, Timestamp ultima_mod_backup) {
		this.id_imagem = id_imagem;
		this.id_descritor = id_descritor;
		this.id_revisor = id_revisor;
		this.estado = estado;
		this.texto = texto;
		this.texto_backup = texto_backup;
		this.ultima_mod_texto = ultima_mod_texto;
		this.ultima_mod_backup = ultima_mod_backup;
	}
	
	public Descricao(int id_descricao, int id_imagem, int id_descritor,
			int id_revisor, int estado, String texto, String texto_backup,
			Timestamp ultima_mod_texto, Timestamp ultima_mod_backup) {
		this.id_descricao = id_descricao;
		this.id_imagem = id_imagem;
		this.id_descritor = id_descritor;
		this.id_revisor = id_revisor;
		this.estado = estado;
		this.texto = texto;
		this.texto_backup = texto_backup;
		this.ultima_mod_texto = ultima_mod_texto;
		this.ultima_mod_backup = ultima_mod_backup;
	}
	
	public Descricao(int id_imagem, int id_descritor, int estado, String texto, String texto_backup,
			Timestamp ultima_mod_texto, Timestamp ultima_mod_backup) {
		this.id_imagem = id_imagem;
		this.id_descritor = id_descritor;
		this.estado = estado;
		this.texto = texto;
		this.texto_backup = texto_backup;
		this.ultima_mod_texto = ultima_mod_texto;
		this.ultima_mod_backup = ultima_mod_backup;
	}
	
	public int getId() {
		return id_descricao;
	}
	public void setId(int id_descricao) {
		this.id_descricao = id_descricao;
	}
	public int getId_imagem() {
		return id_imagem;
	}
	public void setId_imagem(int id_imagem) {
		this.id_imagem = id_imagem;
	}
	public int getId_descritor() {
		return id_descritor;
	}
	public void setId_descritor(int id_descritor) {
		this.id_descritor = id_descritor;
	}
	public int getId_revisor() {
		return id_revisor;
	}
	public void setId_revisor(int id_revisor) {
		this.id_revisor = id_revisor;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTexto_backup() {
		return texto_backup;
	}
	public void setTexto_backup(String texto_backup) {
		this.texto_backup = texto_backup;
	}
	public Timestamp getUltima_mod_texto() {
		return ultima_mod_texto;
	}

	public void setUltima_mod_texto(Timestamp ultima_mod_texto) {
		this.ultima_mod_texto = ultima_mod_texto;
	}

	public Timestamp getUltima_mod_backup() {
		return ultima_mod_backup;
	}

	public void setUltima_mod_backup(Timestamp ultima_mod_backup) {
		this.ultima_mod_backup = ultima_mod_backup;
	}
	@Override
	public String toString() {
		return "Descricao #" + id_descricao + ", Imagem #" + id_imagem + ", Descritor #" + id_descritor 
				 + ", Descritor #" + id_revisor + ", Estado [" + estado + "], \nTexto: '" + texto + "', \nBackup: '" + texto_backup
				 + "',\nData Modificacao: [" + ultima_mod_texto.toString() + "]"
				 + "',\nData Modificacao: [" + ultima_mod_backup.toString() + "]";
	}
	
	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}
