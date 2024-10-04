package com.mrd.server.controllers;

import com.mrd.server.dto.EnrollmentRequestDto;
import com.mrd.server.exceptions.EnrollmentRequestExistsException;
import com.mrd.server.exceptions.EntityNotFoundException;
import com.mrd.server.exceptions.TraineeAlreadyEnrolledException;
import com.mrd.server.models.Course;
import com.mrd.server.models.EnrollmentRequest;
import com.mrd.server.models.Trainee;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.repositories.EnrollmentRequestRepository;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentRequestController {

    private final EnrollmentService enrollmentService;
    private final TraineeRepository traineeRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRequestRepository enrollmentRequestRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createEnrollmentRequest(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        try {
            EnrollmentRequestDto responseDto = enrollmentService.createEnrollmentRequest(enrollmentRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (TraineeAlreadyEnrolledException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EnrollmentRequestExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<EnrollmentRequestDto> approveEnrollmentRequest(@PathVariable Long id) {
        EnrollmentRequestDto approvedRequest = enrollmentService.approveEnrollmentRequest(id);
        return ResponseEntity.ok(approvedRequest);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<EnrollmentRequestDto> rejectEnrollmentRequest(@PathVariable Long id) {
        EnrollmentRequestDto rejectedRequest = enrollmentService.rejectEnrollmentRequest(id);
        return ResponseEntity.ok(rejectedRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentRequestDto>> getAllEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollmentService.getAllEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EnrollmentRequestDto>> getPendingEnrollmentRequests() {
        List<EnrollmentRequestDto> pendingRequests = enrollmentService.getPendingEnrollmentRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<EnrollmentRequestDto>> getApprovedEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollmentService.getApprovedEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<EnrollmentRequestDto>> getRejectedEnrollmentRequests() {
        List<EnrollmentRequestDto> requests = enrollmentService.getRejectedEnrollmentRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTrainees() {
        List<EnrollmentRequestDto> requests = enrollmentService.getEnrolledTrainees();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees/course/{courseId}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTraineesByCourse(@PathVariable Long courseId) {
        List<EnrollmentRequestDto> requests = enrollmentService.getEnrolledTraineesByCourse(courseId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainees/course/{courseId}/status/{status}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrolledTraineesByCourseAndStatus(@PathVariable Long courseId, @PathVariable String status) {
        List<EnrollmentRequestDto> requests = enrollmentService.getEnrolledTraineesByCourseAndStatus(courseId, status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/trainee/{traineeId}")
    public ResponseEntity<List<EnrollmentRequestDto>> getEnrollmentRequestsByTrainee(@PathVariable Long traineeId) {
        List<EnrollmentRequestDto> requests = enrollmentService.getEnrollmentRequestsByTrainee(traineeId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/check-enrollment-status")
    public ResponseEntity<Map<String, String>> checkEnrollmentStatus(
            @RequestParam Long courseId, @RequestParam Long traineeId) {

        // Fetch trainee
        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found"));

        // Fetch course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // Check if trainee is already enrolled in the course
        if (course.getEnrolledTrainees().contains(trainee)) {
            return ResponseEntity.ok(Map.of("status", "ALREADY_ENROLLED"));
        }

        // Check if there is already a pending enrollment request
        Optional<EnrollmentRequest> existingRequest = enrollmentRequestRepository
                .findByTraineeAndCourse(trainee, course);

        if (existingRequest.isPresent()) {
            EnrollmentRequest.Status status = existingRequest.get().getStatus();
            return ResponseEntity.ok(Map.of("status", status.name()));
        }

        // If the trainee is neither enrolled nor has a request, return NOT_ENROLLED
        return ResponseEntity.ok(Map.of("status", "NOT_ENROLLED"));
    }

    @GetMapping("/check-enrollment-exists")
    public boolean checkEnrollmentRequest(
            @RequestParam Long traineeId, @RequestParam Long courseId) {
                return enrollmentRequestRepository.existsByTraineeIdAndCourseId(traineeId, courseId);
    }


}