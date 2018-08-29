package com.danielqueiroz.editora.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.dao.mapper.LivroMapper;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;
import com.danielqueiroz.editora.model.Livro;

@Repository
public class LivroDAO {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameter;
	private SimpleJdbcCall simpleCall;

	@Autowired
	public LivroDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameter = new NamedParameterJdbcTemplate(dataSource);
		this.simpleCall = new SimpleJdbcCall(dataSource);
	}

	
	public String callFunctionTotalLivrosByAutor(int id) {
		simpleCall.withFunctionName("function_conta_livros_autor");

		String texto = simpleCall.executeFunction(String.class, id);
		
		return texto;
	}
	
	public List<String > callProcedureInfoLivro(int idLivro){

		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_id", idLivro);
		simpleCall.withProcedureName("procedure_info_livros");

		Map info = simpleCall.execute(sqlParameterSource);

		
		String titulo =  (String) info.get("out_titulo");
		String autor = (String) info.get("out_autor");
		String editora = (String) info.get("out_editora");
		
		return Arrays.asList(titulo,autor,editora);
	}
	
	
	public Map<String, Object> callProcedureUppercaseTitulo(int idLogin){
		simpleCall.withProcedureName("procedure_uppercase_titulo");
		
		Map<String, Object> map = simpleCall.execute(idLogin);
		
		return map;
	}
	
	public Livro findByTituloAndEdicao(String titulo, int edicao) {
		Livro livro = new Livro();
		livro.setEdicao(edicao);
		livro.setTitulo(titulo);
		
		return namedParameter.queryForObject(
				"select * from Livros where titulo like :titulo and edicao = :edicao",
				new BeanPropertySqlParameterSource(livro), new LivroMapper());
		
	}

	public int alter(Livro livro) {
		return namedParameter.update(
				"update Livros set titulo = :titulo, paginas = :paginas, edicao = :edicao where id_livro = :id",
				new BeanPropertySqlParameterSource(livro));
	}

	public int update(Livro livro, String titulo, int edicao, int paginas) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("titulo", titulo).addValue("paginas", paginas)
				.addValue("edicao", edicao).addValue("id", livro.getId());
		return namedParameter.update(
				"update Livros set titulo = :titulo, paginas = :paginas, edicao = :edicao where id_livro = :id",
				sqlParameterSource);
	}

	public List<Livro> findLivrosbyPaginas(int minimo, int maximo) {

		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("minimo", minimo).addValue("maximo", maximo);

		return namedParameter.query("select * from Livros where paginas between :minimo and :maximo",
				sqlParameterSource, new LivroMapper());
	}

	public List<Livro> findByEdicao(int edicao) {
		return namedParameter.query("select * from Livros where edicao = :edicao",
				new MapSqlParameterSource("edicao", edicao), new LivroMapper());
	}

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
