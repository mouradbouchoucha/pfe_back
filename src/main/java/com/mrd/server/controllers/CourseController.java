package com.mrd.server.controllers;

import com.mrd.server.dto.CourseDto;
import com.mrd.server.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("duration") int duration,
                                                  @RequestParam("startDateTime") String startDateTime,
                                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);

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
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("duration") int duration,
                                                  @RequestParam("startDateTime") String startDateTime,
                                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);

        try {
            courseDto.setStartDateTime(LocalDateTime.parse(startDateTime, DATE_TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }

        courseDto.setImageFile(imageFile);

        CourseDto updatedCourse = courseService.updateCourse(courseDto);
        return ResponseEntity.ok(updatedCourse);
    }
}

