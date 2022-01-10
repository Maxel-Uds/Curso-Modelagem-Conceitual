package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;
    @Autowired
    private TemplateEngine engine;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfitmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
   }

    @Override
    public void sendOrderConfitmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfitmationEmail(obj);
        }
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(sm);
    }

    @Override
    public void sendNewPasswordEmail(String email, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(email, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    protected SimpleMailMessage prepareNewPasswordEmail(String email, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Senha de acesso: " + newPass);
        return sm;
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);

        return mimeMessage;
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return engine.process("email/confirmacaoPedido", context);
    }

}
