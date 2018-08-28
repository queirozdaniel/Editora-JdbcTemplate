package com.danielqueiroz.editora.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.model.LivroAutor;

@Repository
public class LivroAutorDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public LivroAutor save(LivroAutor livroAutor) {

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Livros_Autores")
				.usingColumns("id_livro", "id_autor").usingGeneratedKeyColumns("id_livro_autor");

		Number key = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(livroAutor));

		livroAutor.setIdLivroAutor(key.intValue());

		return livroAutor;
	}

}
