package com.mrd.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ScheduleDto {

    private Long id;

    private double duration;
    private LocalDateTime startDateTime;
    private String location;
    private CourseDto course;
    private TrainerDto trainer;
    private SubjectDto subject;

}
