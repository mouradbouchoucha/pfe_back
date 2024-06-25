package com.mrd.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mrd.server.config.CustomLocalDateTimeDeserializer;
import com.mrd.server.models.Resource;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private Long id;

    private double duration;
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startDateTime;
    private ResourceDto resource;
    private String location;
    private CourseDto course;
    private TrainerDto trainer;
    private SubjectDto subject;

}
