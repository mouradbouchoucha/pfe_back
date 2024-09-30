package com.mrd.server.services;

import com.mrd.server.models.User;

import java.io.File;
import java.util.List;

public interface EmailService {

    void sendVerificationEmail(User user, String token);
    void sendEmail(List<String> to, String subject, String text, File attachment);
}
