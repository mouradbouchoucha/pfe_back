package com.mrd.server.services;

import com.mrd.server.models.Attachment;

public interface AttachementService {

    Attachment createAttachment(Attachment attachment);

    Attachment getAttachmentById(Long id);

}
