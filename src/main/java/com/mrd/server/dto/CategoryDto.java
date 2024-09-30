package com.mrd.server.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private byte[] image;

//    public MultipartFile imageFile;

    private List<CourseDto> courses;
}
