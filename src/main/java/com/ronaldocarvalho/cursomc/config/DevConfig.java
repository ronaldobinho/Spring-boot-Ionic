package com.ronaldocarvalho.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ronaldocarvalho.cursomc.services.DbService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DbService dbservice;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String stratey;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		if (!"create".equals(stratey)) {
			
			return false;
		}
		dbservice.instantiateTestDatabase();
		
		return true;
	}
}
