package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface EmailService {

    void sendOrderConfitmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
    void sendOrderConfitmationHtmlEmail(Pedido obj);
    void sendHtmlEmail(MimeMessage msg);
    void sendNewPasswordEail(Cliente cliente, String newPass);
}
