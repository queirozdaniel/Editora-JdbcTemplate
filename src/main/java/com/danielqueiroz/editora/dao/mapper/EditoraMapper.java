package com.danielqueiroz.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.danielqueiroz.editora.model.Editora;

public class EditoraMapper implements RowMapper<Editora> {

	@Override
	public Editora mapRow(ResultSet rs, int rowNumber) throws SQLException {

		Editora editora = new Editora();
		editora.setId(rs.getInt("id_editora"));
		editora.setRazaoSocial(rs.getString("razao_social"));
		editora.setCidade(rs.getString("cidade"));
		editora.setEmail(rs.getString("email"));

		return editora;
	}

	public class CidadeAndEmailMapper implements RowMapper<Editora> {

		@Override
		public Editora mapRow(ResultSet rs, int rowNum) throws SQLException {
			Editora editora = new Editora();
			editora.setCidade(rs.getString("cidade"));
			editora.setEmail(rs.getString("email"));

			return editora;
		}

	}

}
