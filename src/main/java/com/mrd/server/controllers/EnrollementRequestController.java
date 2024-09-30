package com.mrd.server.controllers;

import com.mrd.server.dto.EnrollmentRequestDto;
import com.mrd.server.services.EnrollementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enrollement")
@RequiredArgsConstructor
public class EnrollementRequestController {

    private final EnrollementService enrollementService;

    @PostMapping("/create")
    public ResponseEntity<EnrollmentRequestDto> createEnrollmentRequest(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        EnrollmentRequestDto createdRequest = enrollementService.createEnrollmentRequest(enrollmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<EnrollmentRequestDto> approveEnrollmentRequest(@PathVariable Long id) {
        EnrollmentRequestDto approvedRequest = enrollementService.approveEnrollmentRequest(id);
        return ResponseEntity.ok(approvedRequest);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<EnrollmentRequestDto> rejectEnrollmentRequest(@PathVariable Long id) {
        EnrollmentRequestDto rejectedRequest = enrollementService.rejectEnrollmentRequest(id);
        return ResponseEntity.ok(rejectedRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentRequestDto>> getAllEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollementService.getAllEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EnrollmentRequestDto>> getPendingEnrollmentRequests() {
        List<EnrollmentRequestDto> pendingRequests = enrollementService.getPendingEnrollmentRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<EnrollmentRequestDto>> getApprovedEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollementService.getApprovedEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<EnrollmentRequestDto>> getRejectedEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollementService.getRejectedEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTrainees() {
        List<EnrollmentRequestDto> requests = enrollementService.getEnrolledTrainees();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees/course/{courseId}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTraineesByCourse(@PathVariable Long courseId) {
        List<EnrollmentRequestDto> requests = enrollementService.getEnrolledTraineesByCourse(courseId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees/course/{courseId}/status/{status}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTraineesByCourseAndStatus(@PathVariable Long courseId, @PathVariable String status) {
        List<EnrollmentRequestDto> requests = enrollementService.getEnrolledTraineesByCourseAndStatus(courseId, status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainee/{traineeId}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrollmentRequestsByTrainee(@PathVariable Long traineeId) {
        List<EnrollmentRequestDto> requests = enrollementService.getEnrollmentRequestsByTrainee(traineeId);
        return ResponseEntity.ok(requests);
    }
}