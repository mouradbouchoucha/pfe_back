package com.mrd.server.repositories;

import com.mrd.server.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachementRepository extends JpaRepository<Attachment, Long> {
}
