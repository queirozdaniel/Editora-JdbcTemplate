package com.danielqueiroz.editora.model;

public class LivroAutor {

	private Integer idLivroAutor;
	private Integer idLivro;
	private Integer idAutor;

	public LivroAutor() {
	}

	public LivroAutor(Integer idLivro, Integer idAutor) {
		this.idLivro = idLivro;
		this.idAutor = idAutor;
	}

	public Integer getIdLivroAutor() {
		return idLivroAutor;
	}

	public void setIdLivroAutor(Integer idLivroAutor) {
		this.idLivroAutor = idLivroAutor;
	}

	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
	
	
}
