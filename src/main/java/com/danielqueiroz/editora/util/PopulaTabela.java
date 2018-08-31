package com.danielqueiroz.editora.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class PopulaTabela {

	@Autowired
	public void setDataSource(DataSource dataSource) {
		DatabasePopulatorUtils.execute(createPopulator(), dataSource);
	}

	private DatabasePopulator createPopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setContinueOnError(true);
		populator.addScript( new ClassPathResource("script-endereco.sql"));
		return populator;
	}
	
}
