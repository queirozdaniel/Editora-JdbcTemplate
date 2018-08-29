package com.danielqueiroz.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.danielqueiroz.editora.model.Livro;

public class LivroMapper  implements RowMapper<Livro> {

	@Override
	public Livro mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Livro livro = new Livro();
		livro.setId(rs.getInt("id_livro"));
		livro.setTitulo(rs.getString("titulo"));
		livro.setPaginas(rs.getInt("paginas"));
		livro.setEdicao(rs.getInt("edicao"));
		
		return livro;
	}

}
