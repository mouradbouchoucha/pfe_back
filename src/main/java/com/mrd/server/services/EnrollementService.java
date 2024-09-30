package com.mrd.server.services;

import com.mrd.server.dto.EnrollmentRequestDto;

import java.util.List;

public interface EnrollementService {
    EnrollmentRequestDto createEnrollmentRequest(EnrollmentRequestDto enrollmentRequestDto);
    EnrollmentRequestDto approveEnrollmentRequest(Long id);
    List<EnrollmentRequestDto> getAllEnrollmentRequests();
    List<EnrollmentRequestDto> getPendingEnrollmentRequests();
    List<EnrollmentRequestDto> getApprovedEnrollmentRequests();
    List<EnrollmentRequestDto> getRejectedEnrollmentRequests();
    List<EnrollmentRequestDto> getEnrolledTrainees();
    List<EnrollmentRequestDto> getEnrolledTraineesByCourse(Long id);
    List<EnrollmentRequestDto> getEnrolledTraineesByCourseAndStatus(Long id, String status);
    List<EnrollmentRequestDto> getEnrollmentRequestsByTrainee(Long id);

    EnrollmentRequestDto rejectEnrollmentRequest(Long id);
}
