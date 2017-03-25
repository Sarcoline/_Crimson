package com.crimson.core.service;

import com.crimson.core.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {


    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(String from, String to, String subject, String msg) throws MessagingException {


        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    @Async
    public void sendConfirmationMail(String to, String token) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String body = String.format("<h1>Your account has been created</h1><h3>Confirm your email</h3>" +
                "<a href='http://localhost:8080/confirm/%s'>Click to confirm</a>", token);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject("Crimson - Confirm your email");
        helper.setText(body, true);
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendPasswordResetMail(UserDTO to, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String body = String.format("<h3>Click to reset your password</h3>" +
                "<a href='http://localhost:8080/user/changePassword?id=%s&token=%s'>Click to reset</a>", to.getId(), token);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to.getEmail());
        helper.setSubject("Crimson - Reset your password");
        helper.setText(body, true);
        mailSender.send(mimeMessage);
    }

    public void sendMailWithEpisodes(String to, String body) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject("Crimson - Daily episode list");
        helper.setText(body, true);
        mailSender.send(mimeMessage);
    }
}
