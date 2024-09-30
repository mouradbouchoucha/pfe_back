package com.mrd.server.repositories;

import com.mrd.server.models.EnrollmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {

    List<EnrollmentRequest> findByStatus(EnrollmentRequest.Status status);
    List<EnrollmentRequest> findByCourseIdAndStatus(Long courseId, EnrollmentRequest.Status status);
    List<EnrollmentRequest> findByTraineeId(Long traineeId);
}
