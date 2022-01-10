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
    void sendNewPasswordEmail(Cliente cliente, String newPass);
    void sendNewPasswordEmail(String email, String newPass);
}
