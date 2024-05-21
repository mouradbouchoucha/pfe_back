package com.mrd.server.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class CourseDto {

    private Long id;
    private String name;
    private String description;
    private int duration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;
    private byte[] image;

    private MultipartFile imageFile;

    private Long category_id;
    private String categoryName;

}
