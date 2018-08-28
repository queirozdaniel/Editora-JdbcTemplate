package com.danielqueiroz.editora.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.dao.mapper.AutorMapper;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;
import com.danielqueiroz.editora.model.Livro;

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

	public Autor findAutorWithLivros(int id) {

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"select a.id_autor, a.nome, a.email, e.id_editora, e.razao_social, e.cidade, e.email as editora_email, l.id_livro, l.titulo, l.edicao, l.paginas from "
						+ "Autores a, Editoras e, Livros l, Livros_Autores as la where l.id_livro = la.id_livro and la.id_autor = a.id_autor "
						+ " and a.id_editora = e.id_editora and a.id_autor = ?",
				id);

		Autor autor = null;

		for (Map row : rows) {

			if (autor == null) {
				autor = new Autor();
				autor.setId((Integer) row.get("id_autor"));
				autor.setNome((String) row.get("nome"));
				autor.setEmail((String) row.get("email"));

				Editora editora = new Editora();
				editora.setId((Integer) row.get("id_editora"));
				editora.setRazaoSocial((String) row.get("razao_social"));
				editora.setCidade((String) row.get("cidade"));
				editora.setEmail((String) row.get("editora_email"));

				autor.setEditora(editora);
			}

			Livro livro = new Livro();
			livro.setId((Integer) row.get("id_autor"));
			livro.setTitulo((String) row.get("titulo"));
			livro.setPaginas((Integer) row.get("paginas"));
			livro.setEdicao((Integer) row.get("edicaor"));

			autor.getLivros().add(livro);
		}

		return autor;
	}

	public Autor save(Autor autor) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("nome", autor.getNome())
				.addValue("email", autor.getEmail()).addValue("id_editora", autor.getEditora().getId());

		Number key = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);

		autor.setId(key.intValue());

		return autor;
	}

	public Autor finById(int id) {
		return jdbcTemplate.queryForObject("select * from Autores where id_autor = ?", new AutorMapper(editoraDAO), id);
	}

	public List<Autor> findAll() {
		return jdbcTemplate.query("select * from Autores", new AutorMapper(editoraDAO));
	}

	public List<Autor> findAutoresByEditora(String razaoSocial) {
		return jdbcTemplate.query(
				"select a.id_autor, a.nome, a.email, a.id_editora, "
						+ "e.id_editora as ed_id_editora, e.razao_social, e.cidade, e.email as ed_email from "
						+ " Autores a, Editoras e where a.id_editora = e.id_editora and e.razao_social like ?",
				new AutorMapper().new AutorWithEditoraMapper(), razaoSocial);
	}

	public int update(Autor autor) {
		return jdbcTemplate.update("update Autores set nome = ?, email = ?, id_editora = ? where id_autor = ?",
				autor.getNome(), autor.getEmail(), autor.getEditora().getId(), autor.getId());
	}

	public int delete(int id) {
		return jdbcTemplate.update("delete from Autores where id_autor = ?", id);
	}

	public Integer getIdByNome(String nome) {
		return jdbcTemplate.queryForObject("select id_autor from Autores where nome like ? ", Integer.class, nome);
	}

}
