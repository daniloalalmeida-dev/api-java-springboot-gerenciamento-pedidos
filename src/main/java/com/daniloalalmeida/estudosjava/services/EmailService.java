package com.daniloalalmeida.estudosjava.services;

import com.daniloalalmeida.estudosjava.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);

}
