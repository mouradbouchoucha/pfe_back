package com.mrd.server.services.impl;


import com.mrd.server.models.User;
import com.mrd.server.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Override
//    public void sendVerificationEmail(User user, String token) {
//        String url = "http://localhost:4200/verify?token=" + token;
//        String subject = "Verify your email";
//        String message = "Please click the following link to verify your email: " + url;
//
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(user.getEmail());
//        email.setSubject(subject);
//        email.setText(message);
//
//        mailSender.send(email);
//    }
}

