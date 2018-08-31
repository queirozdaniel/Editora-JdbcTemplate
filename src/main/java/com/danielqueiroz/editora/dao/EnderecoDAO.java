package com.danielqueiroz.editora.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.editora.model.Endereco;

@Repository
public class EnderecoDAO extends JdbcDaoSupport{

	@Autowired
	public EnderecoDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<Endereco> findAll(){
		
		return  getJdbcTemplate().query("select * from Enderecos", new BeanPropertyRowMapper<Endereco>(Endereco.class));
	}
	
}
