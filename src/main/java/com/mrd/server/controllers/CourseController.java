package com.mrd.server.controllers;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.dto.CourseDto;
import com.mrd.server.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@ModelAttribute CourseDto courseDto) throws IOException {
        CourseDto createdCourse = courseService.createCourse(courseDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        return courseDto != null ? ResponseEntity.ok(courseDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("duration") int duration,
            @RequestPart("startDateTime") LocalDateTime startDateTime,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
            ) throws IOException {

        CourseDto courseDto = new CourseDto();

        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);
        courseDto.setStartDateTime(startDateTime);
        courseDto.setImageFile(imageFile);

        CourseDto updatedCourse = courseService.updateCourse(id, courseDto);
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CourseDto>> getCourseByName(@PathVariable String name) {
        List<CourseDto> coursesDtos = courseService.getCourseByName(name);
        return ResponseEntity.ok().body(coursesDtos);
    }
}
