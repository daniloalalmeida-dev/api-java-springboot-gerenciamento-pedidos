package com.daniloalalmeida.estudosjava.services;

import org.springframework.mail.SimpleMailMessage;

import com.daniloalalmeida.estudosjava.domain.Cliente;
import com.daniloalalmeida.estudosjava.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
