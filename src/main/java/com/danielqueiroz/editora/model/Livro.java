package com.danielqueiroz.editora.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {

	private Integer id;
	private String titulo;
	private Integer edicao;
	private Integer paginas;

	private List<Autor> autores = new ArrayList<>();

	public Livro() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getEdicao() {
		return edicao;
	}

	public void setEdicao(Integer edicao) {
		this.edicao = edicao;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Livro [id=");
		builder.append(id);
		builder.append(", titulo=");
		builder.append(titulo);
		builder.append(", edicao=");
		builder.append(edicao);
		builder.append(", paginas=");
		builder.append(paginas);
		builder.append("]");
		return builder.toString();
	}

}
