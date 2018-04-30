package com.ronaldocarvalho.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ronaldocarvalho.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("$default.sender")
	private String sender;

	@Autowired
	private TemplateEngine TemplateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);

	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		// TODO Auto-generated method stub

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());

		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();
		context.setVariable("pedido", obj);
		return TemplateEngine.process("email/confirmacaoPedido", context);

	}

	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {

			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (Exception e) {
			// TODO: handle exception
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido Confirmado, :" + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}
}
