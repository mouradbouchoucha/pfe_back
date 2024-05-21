package com.mrd.server.services.impl;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.dto.CourseDto;
import com.mrd.server.models.Category;
import com.mrd.server.models.Course;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;


    @Override
    public CourseDto createCourse(CourseDto courseDTO) throws IOException {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setStartDateTime(courseDTO.getStartDateTime());
        course.setCategory(Category.builder().id(courseDTO.getCategory_id()).build());
        byte[] imageData = courseDTO.getImageFile().getBytes();
        course.setImage(imageData);

        return courseRepository.save(course).getDto();
    }

    @Override
    public CourseDto getCourseById(Long id) {
        return Objects.requireNonNull(courseRepository.findById(id).orElse(null)).getDto();
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDTO) throws IOException {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse == null) {
            return null; // or throw an exception
        }
        BeanUtils.copyProperties(courseDTO, existingCourse);
        if (courseDTO.getImageFile() != null) {
            byte[] imageData = courseDTO.getImageFile().getBytes();
            courseDTO.setImage(imageData);
        }
        return convertToDTO(courseRepository.save(existingCourse));
    }

    private CourseDto convertToDTO(Course course) {
        CourseDto courseDTO = new CourseDto();
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }
    @Override
    public List<CourseDto> getCourseByName(String name) {
        List<Course> courses = courseRepository.findAllByNameOrDescriptionContainingIgnoreCase(name);
        return courses.stream().map(Course::getDto).collect(Collectors.toList());
    }
}
