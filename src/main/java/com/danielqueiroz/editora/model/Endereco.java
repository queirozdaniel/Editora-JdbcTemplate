package com.danielqueiroz.editora.model;

public class Endereco {

	private Integer idEndereco;
	private String logradouro;
	private int numero;
	private String cidade;
	private Integer idAutor;

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Endereco [idEndereco=");
		builder.append(idEndereco);
		builder.append(", logradouro=");
		builder.append(logradouro);
		builder.append(", numero=");
		builder.append(numero);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append(", idAutor=");
		builder.append(idAutor);
		builder.append("]");
		return builder.toString();
	}

}
