package com.danielqueiroz.editora.model;

public class Editora {

	private Integer id;
	private String razaoSocial;
	private String cidade;
	private String email;

	public Editora() {
	}

	public Editora(String razaoSocial, String cidade, String email) {
		super();
		this.razaoSocial = razaoSocial;
		this.cidade = cidade;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Editora [id=");
		builder.append(id);
		builder.append(", razaoSocial=");
		builder.append(razaoSocial);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

}
