package de.kozdemir.springsecuritydemo.service;

import de.kozdemir.springsecuritydemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine; //Thymeleaf


    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("ike@localhost");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    public void sendHtmlEmail(String to, String subject) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("ike@localhost");
        helper.setTo(to);
        helper.setSubject(subject);
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/activate/1234");
        helper.setText(renderTemplateEmail(vars, "email-register"), true);
        mailSender.send(msg);
    }

    public void sendHtmlRegisterEmail(User user, UUID token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("ike@localhost");
        helper.setTo(user.getEmail());
        helper.setSubject("Registrierung");
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/activate?token=" + token);
        helper.setText(renderTemplateEmail(vars, "email-register"), true);
        mailSender.send(msg);
    }

    public void sendHtmlForgotEmail(User user, UUID token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("ike@localhost");
        helper.setTo(user.getEmail());
        helper.setSubject("Passwort zurücksetzen");
        Map<String, Object> vars = new HashMap<>();
        vars.put("link", "http://localhost:8080/forgot/reset?token=" + token);
        helper.setText(renderTemplateEmail(vars, "email-forgot"), true);
        mailSender.send(msg);
    }

    /**
     * @param vars     Map von Variablen
     * @param template Thymeleaf-Template
     * @return HTML aus dem Template. Platzhalter sind durch konkrete Werte gefüllt
     */
    public String renderTemplateEmail(Map<String, Object> vars, String template) {
        Context context = new Context();
        context.setVariables(vars);
        String html = springTemplateEngine.process(template, context);
        return html;

    }
}
