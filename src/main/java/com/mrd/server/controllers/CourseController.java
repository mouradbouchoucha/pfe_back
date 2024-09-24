package com.mrd.server.controllers;

import com.mrd.server.dto.CourseDto;
import com.mrd.server.models.Category;
import com.mrd.server.models.Course;
import com.mrd.server.models.EnrollmentRequest;
import com.mrd.server.models.Trainee;
import com.mrd.server.repositories.CategoryRepository;
import com.mrd.server.repositories.EnrollmentRequestRepository;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CategoryRepository categoryRepository;
    private final EnrollmentRequestRepository enrollmentRequestRepository;
    private final TraineeRepository traineeRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("duration") int duration,
                                                  @RequestParam("startDateTime") String startDateTime,
                                                  @RequestParam("category_id") Long category_id,
                                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);
        courseDto.setCategory_id(category_id);
        courseDto.setCategoryName(categoryRepository.findById(category_id).get().getName());
        try {
            courseDto.setStartDateTime(LocalDateTime.parse(startDateTime, DATE_TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }

        courseDto.setImageFile(imageFile);

        CourseDto createdCourse = courseService.createCourse(courseDto);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/all_SortedByCreatedAt")
    public ResponseEntity<List<CourseDto>> getAllCoursesSortedByCreatedAt() {
        List<CourseDto> courses = courseService.getAllCoursesOrderByCreatedAt();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> getCoursesByName(@RequestParam("name") String name) {
        List<CourseDto> courses = courseService.getCourseByName(name);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam("startDateTime") String startDateTime,
            @RequestParam("category_id") Long category_id,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);
        courseDto.setCategory_id(category_id);

        // Fetch category and handle the case where it is not found
        Optional<Category> categoryOptional = categoryRepository.findById(category_id);
        if (categoryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        courseDto.setCategoryName(categoryOptional.get().getName());

        try {
            courseDto.setStartDateTime(LocalDateTime.parse(startDateTime, DATE_TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }

        courseDto.setImageFile(imageFile);

        CourseDto updatedCourse = courseService.updateCourse(courseDto);
        return ResponseEntity.ok(updatedCourse);
    }

    @PostMapping("/approve-enrollment")
    public ResponseEntity<String> approveEnrollment(@RequestParam Long requestId) {
        EnrollmentRequest request = enrollmentRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(EnrollmentRequest.Status.APPROVED);
        enrollmentRequestRepository.save(request);

        Trainee trainee = request.getTrainee();
        Course course = request.getCourse();

        trainee.getEnrolledCourses().add(course);
        traineeRepository.save(trainee);

        return ResponseEntity.ok("Enrollment approved");
    }

    @PostMapping("/reject-enrollment")
    public ResponseEntity<String> rejectEnrollment(@RequestParam Long requestId) {
        EnrollmentRequest request = enrollmentRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(EnrollmentRequest.Status.REJECTED);
        enrollmentRequestRepository.save(request);

        return ResponseEntity.ok("Enrollment rejected");
    }

}

