package com.danielqueiroz.editora.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.dao.mapper.EditoraMapper;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;

@Repository
@PropertySource("${classpath:sql/editora.xml}")
public class EditoraDAO {

	@Value("${sql.insert}")
	private String sqlInsert;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveBatch(List<Editora> editoras) {
		SimpleJdbcInsert simple = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("Editoras")
				.usingColumns("razao_social","cidade","email");
		
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(editoras.toArray());
		
		simple.executeBatch(batch);
	}
	
	public void insertBatch(List<Editora> editoras) {
		jdbcTemplate.batchUpdate(sqlInsert, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Editora editora = editoras.get(i);
				ps.setString(1, editora.getRazaoSocial());
				ps.setString(2, editora.getCidade());
				ps.setString(3, editora.getEmail());
			}
			
			@Override
			public int getBatchSize() {
				return editoras.size();
			}
		});
	}

	public Editora findEditoraWithAutoresPaginados(int id, int page, int size) {
		List<Map<String, Object>> rows = jdbcTemplate
				.queryForList(
						"select e.id_editora, e.razao_social, e.cidade, e.email, "
								+ "a.id_autor, a.nome, a.email as autor_email, a.id_editora as autor_id_editora"
								+ " from Editoras e, Autores a where e.id_editora = a.id_editora and e.id_editora = ? limit ?,?",
						id, page * size, size);

		Editora editora = null;

		for (Map row : rows) {

			if (editora == null) {
				editora = new Editora();
				editora.setId((Integer) row.get("id_editora"));
				editora.setCidade((String) row.get("cidade"));
				editora.setRazaoSocial((String) row.get("razao_social"));
				editora.setEmail((String) row.get("email"));
			}

			Autor autor = new Autor();
			autor.setId((Integer) row.get("id_autor"));
			autor.setNome((String) row.get("nome"));
			autor.setEmail((String) row.get("autor_email"));

			editora.getAutores().add(autor);
		}

		return editora;

	}

	public Editora findEditoraWithAutores(int id) {
		List<Map<String, Object>> rows = jdbcTemplate
				.queryForList(
						"select e.id_editora, e.razao_social, e.cidade, e.email, "
								+ "a.id_autor, a.nome, a.email as autor_email, a.id_editora as autor_id_editora"
								+ " from Editoras e, Autores a where e.id_editora = a.id_editora and e.id_editora = ? ",
						id);

		Editora editora = null;

		for (Map row : rows) {

			if (editora == null) {
				editora = new Editora();
				editora.setId((Integer) row.get("id_editora"));
				editora.setCidade((String) row.get("cidade"));
				editora.setRazaoSocial((String) row.get("razao_social"));
				editora.setEmail((String) row.get("email"));
			}

			Autor autor = new Autor();
			autor.setId((Integer) row.get("id_autor"));
			autor.setNome((String) row.get("nome"));
			autor.setEmail((String) row.get("autor_email"));

			editora.getAutores().add(autor);
		}

		return editora;
	}

	public int delete(int id) {
		return jdbcTemplate.update("delete from Editoras where id_editora = ? ", id);
	}

	public int update(Editora editora, String razao_social, String cidade, String email) {
		return jdbcTemplate.update("update Editoras set razao_social = ?, cidade = ?, email = ? where id_editora = ?",
				razao_social, cidade, email, editora.getId());
	}

	public List<Editora> findCidadesAndEmails() {
		return jdbcTemplate.query("select cidade, email from Editoras", new EditoraMapper().new CidadeAndEmailMapper());
	}

	public Editora findCidadeAndEmailForId(int id) {
		return jdbcTemplate.queryForObject("select cidade, email from Editoras where id_editora = ?",
				new Integer[] { id }, new EditoraMapper().new CidadeAndEmailMapper());
	}

	public List<String> findCidadeAndEmailById(int id) {
		return jdbcTemplate.queryForObject("select cidade, email from Editoras where id_editora = ?",
				new Integer[] { id }, new RowMapper<List<String>>() {
					@Override
					public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
						return Arrays.asList(rs.getString("cidade"), rs.getString("email"));
					}

				});
	}

	public List<String> findEmails() {
		return jdbcTemplate.queryForList("select email from Editoras", String.class);
	}

	public String findEmailById(int id) {
		return jdbcTemplate.queryForObject("select email from Editoras where id_editora = ?", String.class, id);
	}

	public int count() {
		return jdbcTemplate.queryForObject("select count(*) from Editoras", Integer.class);
	}

	public List<Editora> findByRazaoSocial(String razaoSocial) {
		return jdbcTemplate.query("select * from Editoras where razao_social like '%' ? '%'", new EditoraMapper(),
				razaoSocial);
	}

	public List<Editora> findByCidade(String cidade1, String cidade2) {
		return jdbcTemplate.query("select * from Editoras where cidade like ? or cidade like ?", new EditoraMapper(),
				cidade1, cidade2);
	}

	public Editora findById(int id) {
		return jdbcTemplate.queryForObject("select * from Editoras where id_editora = ? ", new EditoraMapper(), id);
	}

	public List<Editora> findAll() {
		return jdbcTemplate.query("select * from Editoras", new EditoraMapper());
	}

	public Editora add(Editora editora) {
		SimpleJdbcInsert simpleJdbc = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbc.setTableName("Editoras");
		simpleJdbc.setColumnNames(Arrays.asList("razao_social", "cidade", "email"));
		simpleJdbc.setGeneratedKeyName("id_editora");

		Number key = simpleJdbc.executeAndReturnKey(new BeanPropertySqlParameterSource(editora));

		editora.setId(key.intValue());

		return editora;
	}

	public Integer save(Editora editora) {
		SimpleJdbcInsert simpleJdbc = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbc.setTableName("Editoras");
		simpleJdbc.setColumnNames(Arrays.asList("razao_social", "cidade", "email"));
		simpleJdbc.setGeneratedKeyName("id_editora");

		Number key = simpleJdbc.executeAndReturnKey(new BeanPropertySqlParameterSource(editora));

		return key.intValue();
	}

	public int insert(Editora editora) {
		return jdbcTemplate.update(sqlInsert, editora.getRazaoSocial(), editora.getCidade(), editora.getEmail());
	}

}
