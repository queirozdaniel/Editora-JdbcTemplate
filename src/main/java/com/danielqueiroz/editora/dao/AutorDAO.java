package com.danielqueiroz.editora.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.dao.mapper.AutorMapper;
import com.danielqueiroz.editora.model.Autor;

@Repository
public class AutorDAO {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	
	@Autowired
	private EditoraDAO editoraDAO;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("Autores")
				// .usingColumns("nome", "email", "id_editora")
				.usingGeneratedKeyColumns("id_autor");
	}

	public Autor save(Autor autor) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("nome", autor.getNome())
				.addValue("email", autor.getEmail()).addValue("id_editora", autor.getEditora().getId());

		Number key = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);

		autor.setId(key.intValue());

		return autor;
	}

	public List<Autor> findAll() {
		return jdbcTemplate.query("select * from Autores", new AutorMapper(editoraDAO));
	}

	public List<Autor> findAutoresByEditora(){
		return jdbcTemplate.query("select a.id_autor, a.nome, a.email, a.id_editora, "
				+ "e.id_editora as ed_id_editora, e.razao_social, e.cidade, e.email as ed_email from "
				+ " Autores a, Editoras e where a.id_editora = e.id_editora and e.razao_social like ?", new AutorMapper(editoraDAO));
	}
	
}
