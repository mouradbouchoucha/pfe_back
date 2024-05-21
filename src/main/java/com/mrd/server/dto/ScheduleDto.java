package com.mrd.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private Long id;
    private String name;
    private int duration;
    private LocalDateTime startDateTime;
    private String location;
    private CourseDto course;

}
