package com.mrd.server.services.impl;

import com.mrd.server.dto.CourseDto;
import com.mrd.server.models.Course;
import com.mrd.server.repositories.CategoryRepository;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CourseDto createCourse(CourseDto courseDto) throws IOException {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setStartDateTime(courseDto.getStartDateTime());
        course.setCategory(categoryRepository.findById(courseDto.getCategory_id()).orElseThrow());
        if (courseDto.getImageFile() != null && !courseDto.getImageFile().isEmpty()) {
            course.setImage(courseDto.getImageFile().getBytes());
        }

        course = courseRepository.save(course);
        return course.getDto();
    }


    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getDto();
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(Course::getDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getCourseByName(String name) {
        return courseRepository.findAllByNameContainingIgnoreCase(name).stream().map(Course::getDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) throws IOException {
        Course course = courseRepository.findById(courseDto.getId()).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setStartDateTime(courseDto.getStartDateTime());
        course.setCategory(categoryRepository.findById(courseDto.getCategory_id()).orElseThrow());
        if (courseDto.getImageFile() != null && !courseDto.getImageFile().isEmpty()) {
            course.setImage(courseDto.getImageFile().getBytes());
        }

        course = courseRepository.save(course);
        return course.getDto();
    }

    @Override
    public List<CourseDto> getAllCoursesOrderByCreatedAt() {
        List<Course> courses = courseRepository.findAllByOrderByCreatedAtDesc();
        return courses.stream().map(Course::getDto).collect(Collectors.toList());
    }


}