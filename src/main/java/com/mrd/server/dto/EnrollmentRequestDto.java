package com.mrd.server.dto;

import lombok.Data;

@Data
public class EnrollmentRequestDto {
    private Long id;
    private Long traineeId;   // Trainee's ID for creating a new request
    private Long courseId;    // Course's ID for creating a new request
    private String status;    // PENDING, APPROVED, or REJECTED
}
