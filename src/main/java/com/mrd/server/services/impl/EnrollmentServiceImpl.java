package com.mrd.server.services.impl;

import com.mrd.server.dto.EnrollmentRequestDto;
import com.mrd.server.exceptions.EnrollmentRequestExistsException;
import com.mrd.server.exceptions.EntityNotFoundException;
import com.mrd.server.exceptions.TraineeAlreadyEnrolledException;
import com.mrd.server.models.*;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.repositories.EnrollmentRequestRepository;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.repositories.UserRepository;
import com.mrd.server.services.EmailService;
import com.mrd.server.services.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRequestRepository enrollmentRequestRepository;
    private final TraineeRepository traineeRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");


    @Override
    @Transactional
    public EnrollmentRequestDto createEnrollmentRequest(EnrollmentRequestDto enrollmentRequestDto) {
        // Fetch trainee and course
        Trainee trainee = traineeRepository.findById(enrollmentRequestDto.getTraineeId())
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));
        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        // Check if trainee is already enrolled in the course
        if (course.getEnrolledTrainees().contains(trainee)) {
            throw new TraineeAlreadyEnrolledException("Trainee is already enrolled in this course.");
        }

        // Check if there is already a pending or approved enrollment request
        Optional<EnrollmentRequest> existingRequest = enrollmentRequestRepository.findByTraineeAndCourse(trainee, course);
        if (existingRequest.isPresent()) {
            throw new EnrollmentRequestExistsException("Trainee has already submitted an enrollment request for this course.");
        }

        // Create new EnrollmentRequest
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
        enrollmentRequest.setTrainee(trainee);
        enrollmentRequest.setCourse(course);
        enrollmentRequest.setStatus(EnrollmentRequest.Status.PENDING); // Default status is PENDING

        // Save to the database
        EnrollmentRequest savedRequest = enrollmentRequestRepository.save(enrollmentRequest);

        // Create response DTO
        EnrollmentRequestDto responseDto = new EnrollmentRequestDto();
        responseDto.setId(savedRequest.getId());
        responseDto.setTraineeId(trainee.getId());
        responseDto.setCourseId(course.getId());
        responseDto.setStatus(savedRequest.getStatus().name());



        sendEmails(savedRequest);
        return responseDto;
    }

    @Async
    public void sendEmails(EnrollmentRequest enrollmentRequest) {
        String subject = "Enrollment Request Created at ";
        String message = String.format("Dear ,<br>Your enrollment request for the course <b>%s</b> has been successfully created.<br><br>" +
                        "<b>Course Details</b><br>" +
                        "Course Name: %s<br>" +
                        "Description: %s<br>" +
                        "Start Date: %s<br>" +
                        "Duration: %s<br><br>" +
                        "Regards,<br>" +
                        "Mrd Team",
//                Date.from(Instant.now()).toString(),
                enrollmentRequest.getTrainee().getFirstName() + " " + enrollmentRequest.getTrainee().getLastName(),
                enrollmentRequest.getCourse().getName(),
                enrollmentRequest.getCourse().getName(),
                enrollmentRequest.getCourse().getDescription(),
                enrollmentRequest.getCourse().getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                enrollmentRequest.getCourse().getDuration() + " Hour");

        List<String> recipients = new ArrayList<>();
        recipients.add(enrollmentRequest.getTrainee().getEmail());
        emailService.sendEmail(recipients, subject, message, null);

        List<User> admins = userRepository.findAllByRole(Role.ADMIN);
        List<String> adminEmails = admins.stream().map(User::getEmail).toList();
        String messageAdmin = String.format("Enrollment Request Created for course %s<br><br> by %s %s",
                enrollmentRequest.getCourse().getName(), enrollmentRequest.getTrainee().getFirstName(), enrollmentRequest.getTrainee().getLastName());
        emailService.sendEmail(adminEmails, "Enrollment Request Created", messageAdmin, null);
    }
    @Override
    public boolean checkEnrollmentRequestExists(Long traineeId, Long courseId) {
        return enrollmentRequestRepository.existsByTraineeIdAndCourseId(traineeId, courseId);
    }

    @Override
    @Transactional
    public EnrollmentRequestDto approveEnrollmentRequest(Long id) {
        // Find the enrollment request by ID
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment request not found"));

        // Update status to APPROVED
        enrollmentRequest.setStatus(EnrollmentRequest.Status.APPROVED);

        // Save the updated request
        EnrollmentRequest updatedRequest = enrollmentRequestRepository.save(enrollmentRequest);

        // Send approval email to the trainee
        Trainee trainee = enrollmentRequest.getTrainee();
        Course course = enrollmentRequest.getCourse();
        String subject = "Enrollment Approved";
        String message = String.format("Dear %s, your enrollment for the course %s has been approved.",
                trainee.getFirstName() + " " + trainee.getLastName(), course.getName());



        // Convert to DTO
        EnrollmentRequestDto responseDto = new EnrollmentRequestDto();
        responseDto.setId(updatedRequest.getId());
        responseDto.setTraineeId(updatedRequest.getTrainee().getId());
        responseDto.setCourseId(updatedRequest.getCourse().getId());
        responseDto.setStatus(updatedRequest.getStatus().name());

        List<String> recipients = new ArrayList<>();
        recipients.add(trainee.getEmail());
        emailService.sendEmail(recipients, subject, message,null);

        return responseDto;
    }

    @Override
    @Transactional
    public EnrollmentRequestDto rejectEnrollmentRequest(Long id) {
        // Find the enrollment request by ID
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment request not found"));

        // Update status to REJECTED
        enrollmentRequest.setStatus(EnrollmentRequest.Status.REJECTED);

        // Save the updated request
        EnrollmentRequest updatedRequest = enrollmentRequestRepository.save(enrollmentRequest);
        System.out.println("updatedRequest: " + updatedRequest);
        // Send rejection email to the trainee
        Trainee trainee = enrollmentRequest.getTrainee();

        Course course = enrollmentRequest.getCourse();
        String subject = "Enrollment Rejected";
        String message = String.format("Dear %s, your enrollment for the course %s has been rejected.",
                trainee.getFirstName() + " " + trainee.getLastName(), course.getName());



        // Convert to DTO
        EnrollmentRequestDto responseDto = new EnrollmentRequestDto();
        responseDto.setId(updatedRequest.getId());
        responseDto.setTraineeId(updatedRequest.getTrainee().getId());
        responseDto.setCourseId(updatedRequest.getCourse().getId());
        responseDto.setStatus(updatedRequest.getStatus().name());

        List<String> recipients = new ArrayList<>();
        recipients.add(trainee.getEmail());
        emailService.sendEmail(recipients, subject, message,null);

        return responseDto;
    }

    public List<EnrollmentRequestDto> getAllEnrollmentRequests() {
        List<EnrollmentRequest> enrollmentRequests = enrollmentRequestRepository.findAll();
        return enrollmentRequests.stream()
                .map(enrollmentRequest -> {
                    EnrollmentRequestDto enrollmentRequestDto = new EnrollmentRequestDto();
                    enrollmentRequestDto.setId(enrollmentRequest.getId());
                    enrollmentRequestDto.setTraineeId(enrollmentRequest.getTrainee().getId());
                    enrollmentRequestDto.setCourseId(enrollmentRequest.getCourse().getId());
                    enrollmentRequestDto.setStatus(enrollmentRequest.getStatus().name());
                    return enrollmentRequestDto;
                })
                .toList();
    }

//    public EnrollmentRequestDto rejectEnrollmentRequest(Long id) {
//        // Find the enrollment request by ID
//        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Enrollment request not found"));
//
//        // Update status to REJECTED
//        enrollmentRequest.setStatus(EnrollmentRequest.Status.REJECTED);
//
//        // Save the updated request
//        EnrollmentRequest updatedRequest = enrollmentRequestRepository.save(enrollmentRequest);
//
//        // Convert to DTO
//        EnrollmentRequestDto responseDto = new EnrollmentRequestDto();
//        responseDto.setId(updatedRequest.getId());
//        responseDto.setTraineeId(updatedRequest.getTrainee().getId());
//        responseDto.setCourseId(updatedRequest.getCourse().getId());
//        responseDto.setStatus(updatedRequest.getStatus().name());
//
//        //send mail
//        return responseDto;
//    }

    @Override
    public List<EnrollmentRequestDto> getPendingEnrollmentRequests() {
        // Find all enrollment requests with PENDING status
        List<EnrollmentRequest> pendingRequests = enrollmentRequestRepository.findByStatus(EnrollmentRequest.Status.PENDING);

        // Convert the list of entities to a list of DTOs
        return pendingRequests.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentRequestDto> getApprovedEnrollmentRequests() {
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByStatus(EnrollmentRequest.Status.APPROVED);
        return convertToDtoList(requests);
    }

    @Override
    public List<EnrollmentRequestDto> getRejectedEnrollmentRequests() {
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByStatus(EnrollmentRequest.Status.REJECTED);
        return convertToDtoList(requests);
    }

    @Override
    public List<EnrollmentRequestDto> getEnrolledTrainees() {
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByStatus(EnrollmentRequest.Status.APPROVED);
        return convertToDtoList(requests);
    }

    @Override
    public List<EnrollmentRequestDto> getEnrolledTraineesByCourse(Long courseId) {
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByCourseIdAndStatus(courseId, EnrollmentRequest.Status.APPROVED);
        return convertToDtoList(requests);
    }

    @Override
    public List<EnrollmentRequestDto> getEnrolledTraineesByCourseAndStatus(Long courseId, String status) {
        EnrollmentRequest.Status reqStatus = EnrollmentRequest.Status.valueOf(status.toUpperCase());
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByCourseIdAndStatus(courseId, reqStatus);
        return convertToDtoList(requests);
    }

    @Override
    public List<EnrollmentRequestDto> getEnrollmentRequestsByTrainee(Long traineeId) {
        List<EnrollmentRequest> requests = enrollmentRequestRepository.findByTraineeId(traineeId);
        return convertToDtoList(requests);
    }

    // Helper method to convert entity list to DTO list
    private List<EnrollmentRequestDto> convertToDtoList(List<EnrollmentRequest> requests) {
        return requests.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private EnrollmentRequestDto convertToDto(EnrollmentRequest request) {
        EnrollmentRequestDto dto = new EnrollmentRequestDto();
        dto.setId(request.getId());
        dto.setTraineeId(request.getTrainee().getId());
        dto.setCourseId(request.getCourse().getId());
        dto.setStatus(request.getStatus().name());
        return dto;
    }
}