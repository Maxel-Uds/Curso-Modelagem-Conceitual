package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendOrderConfitmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
}
