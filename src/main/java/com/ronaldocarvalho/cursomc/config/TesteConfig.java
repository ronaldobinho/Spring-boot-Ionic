package com.ronaldocarvalho.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ronaldocarvalho.cursomc.services.DbService;
import com.ronaldocarvalho.cursomc.services.EmailService;
import com.ronaldocarvalho.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TesteConfig {

	@Autowired
	private DbService dbservice;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		dbservice.instantiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
