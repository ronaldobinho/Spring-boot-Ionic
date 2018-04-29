package com.ronaldocarvalho.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ronaldocarvalho.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
