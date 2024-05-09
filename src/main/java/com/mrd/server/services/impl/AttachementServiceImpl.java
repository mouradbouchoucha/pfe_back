package com.mrd.server.services.impl;

import com.mrd.server.models.Attachment;
import com.mrd.server.repositories.AttachementRepository;
import com.mrd.server.services.AttachementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachementServiceImpl implements AttachementService {
    private final AttachementRepository attachementRepository;

    public Attachment createAttachment(Attachment attachment) {
        return attachementRepository.save(attachment);
    }

    public Attachment getAttachmentById(Long id) {
        return attachementRepository.findById(id).orElse(null);
    }

}
