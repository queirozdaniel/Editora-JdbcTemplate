package com.danielqueiroz.editora.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String email;
	private Editora editora;

	private List<Livro> livros = new ArrayList<>();

	public Autor() {
	}

	public Autor(String nome, String email, Editora editora) {
		this.nome = nome;
		this.email = email;
		this.editora = editora;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Autor [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", email=");
		builder.append(email);
		builder.append(", editora=");
		builder.append(editora);
		builder.append("]");
		return builder.toString();
	}

}
