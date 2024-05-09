package com.mrd.server.controllers;

import com.mrd.server.models.Attachment;
import com.mrd.server.services.impl.AttachementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/attachements")
@RequiredArgsConstructor
public class AttachementController {
    private final AttachementServiceImpl attachementService;

    @PostMapping("/create")
    public Attachment createAttachment(@RequestParam("file") MultipartFile file, Attachment attachment) throws IOException {
        attachment.setContent(file.getBytes()); // Set the content of the attachment with file bytes
        return attachementService.createAttachment(attachment);
    }

    @GetMapping("/{id}")
    public Attachment getAttachmentById(@PathVariable Long id) {
        return attachementService.getAttachmentById(id);
    }
}
