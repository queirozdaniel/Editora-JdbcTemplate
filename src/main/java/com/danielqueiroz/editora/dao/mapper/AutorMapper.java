package com.danielqueiroz.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.danielqueiroz.editora.dao.EditoraDAO;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;

public class AutorMapper implements RowMapper<Autor> {

	private EditoraDAO editoraDAO;

	public AutorMapper(EditoraDAO dao) {
		this.editoraDAO = dao;
	}
	
	@Override
	public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
		Autor autor = new Autor();
		autor.setId(rs.getInt("id_autor"));
		autor.setNome(rs.getString("nome"));
		autor.setEmail(rs.getString("email"));

		Editora editora = editoraDAO.findById(rs.getInt("id_editora"));
		
		autor.setEditora(editora);
		
		return autor;
	}

	public class AutorWithEditoraMapper implements RowMapper<Autor>{

		@Override
		public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Autor autor = new Autor();
			autor.setId(rs.getInt("id_autor"));
			autor.setNome(rs.getString("nome"));
			autor.setEmail(rs.getString("email"));
			
			Editora editora = new Editora();
			editora.setId(rs.getInt("ed_id_editora"));
			editora.setRazaoSocial(rs.getString("razao_social"));
			editora.setCidade(rs.getString("cidade"));
			editora.setEmail(rs.getString("ed_email"));
			
			autor.setEditora(editora);
			
			return autor;
		}
		
	}
	
}
