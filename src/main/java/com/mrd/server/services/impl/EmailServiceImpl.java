package com.mrd.server.services.impl;


import com.mrd.server.models.User;
import com.mrd.server.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.angus.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(User user, String token) {
        String url = "http://localhost:4200/verify?token=" + token;
        String subject = "Verify your email";
        String message = "Please click the following link to verify your email: " + url;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    public void sendEmail(List<String> to, String subject, String text, File attachment) {
        MimeMessage message = mailSender.createMimeMessage();

        // Regex to validate email format (RFC 5321 basic compliance)
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        try {
            // Validate all email addresses
            for (String recipient : to) {
                if (!emailPattern.matcher(recipient).matches()) {
                    throw new IllegalArgumentException("Invalid email address: " + recipient);
                }
            }

            // Create a helper to set up the message
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message
            helper.setTo(to.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(text, true); // true indicates that the text is in HTML format if needed

            // Check if an attachment is provided
            if (attachment != null && attachment.exists()) {
                helper.addAttachment(attachment.getName(), attachment);
            }

            // Send the email
            mailSender.send(message);

        } catch (IllegalArgumentException e) {
            System.out.println("Failed to send email due to invalid address: " + e.getMessage());

        } catch (SendFailedException e) {
            // Handle SendFailedException, specifically for invalid addresses
            System.out.println("Failed to send email: Invalid addresses. " + e.getMessage());

        } catch (MessagingException e) {
            e.printStackTrace(); // Handle general messaging exceptions
        }
    }

//    public void sendEmail(List<String> to, String subject, String text) {
//
//    }
}

