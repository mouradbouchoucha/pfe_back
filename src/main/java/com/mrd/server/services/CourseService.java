package com.mrd.server.services;

import com.mrd.server.dto.CourseDto;

import java.io.IOException;
import java.util.List;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDTO) throws IOException;
    CourseDto getCourseById(Long id);
    List<CourseDto> getAllCourses();
    void deleteCourse(Long id);
    List<CourseDto> getCourseByName(String name);
    CourseDto updateCourse( CourseDto courseDTO) throws IOException;
}
