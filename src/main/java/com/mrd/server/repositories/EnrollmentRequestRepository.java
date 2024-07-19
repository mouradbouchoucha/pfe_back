package com.mrd.server.repositories;

import com.mrd.server.models.EnrollmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {
}
