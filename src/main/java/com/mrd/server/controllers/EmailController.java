package com.mrd.server.controllers;

import com.mrd.server.models.User;
import com.mrd.server.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam("emails") String emails,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        try {
            List<String> emailList = Arrays.asList(emails.split(","));
            // Convert MultipartFile to File if an attachment is present
            System.out.println(attachment==null);
            File attachmentFile = null;
            if (attachment != null && !attachment.isEmpty()) {
                attachmentFile = convertMultipartFileToFile(attachment);
            }

            // Create a dummy user object for email purposes


            // Call the service method to send the email
            emailService.sendEmail(emailList, subject, message, attachmentFile);

            // Return success response
            return ResponseEntity.ok("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    // Utility method to convert MultipartFile to File
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }
}
