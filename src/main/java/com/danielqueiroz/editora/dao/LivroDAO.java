package com.danielqueiroz.editora.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;
import com.danielqueiroz.editora.model.Livro;

@Repository
public class LivroDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Livro save(Livro livro) {

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Livros")
				.usingColumns("titulo", "edicao", "paginas").usingGeneratedKeyColumns("id_livro");

		Number key = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(livro));

		livro.setId(key.intValue());

		return livro;
	}

	public Livro findLivroWithAutores(int id) {

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"select l.id_livro, l.titulo, l.edicao, l.paginas, a.id_autor, a.nome, a.email as autor_email, e.id_editora, e.razao_social, e.cidade, e.email from "
						+ "Livros l, Autores a, Editoras e, Livros_Autores as la where l.id_livro = la.id_livro and la.id_autor = a.id_autor "
						+ " and a.id_editora = e.id_editora and l.id_livro = ?",
				id);

		Livro livro = null;

		for (Map row : rows) {

			if (livro == null) {
				livro = new Livro();
				livro.setId((Integer) row.get("id_livro"));
				livro.setTitulo((String) row.get("titulo"));
				livro.setEdicao((Integer) row.get("edicao"));
				livro.setPaginas((Integer) row.get("paginas"));
			}

			Autor autor = new Autor();
			autor.setId((Integer) row.get("id_autor"));
			autor.setNome((String) row.get("nome"));
			autor.setEmail((String) row.get("autor_email"));

			Editora editora = new Editora();
			editora.setId((Integer) row.get("id_editora"));
			editora.setRazaoSocial((String) row.get("razao_social"));
			editora.setCidade((String) row.get("cidade"));
			editora.setEmail((String) row.get("email"));

			autor.setEditora(editora);

			livro.getAutores().add(autor);
		}

		return livro;
	}

}
