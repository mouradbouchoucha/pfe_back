package com.mrd.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mrd.server.config.CustomLocalDateTimeDeserializer;
import com.mrd.server.models.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class ScheduleDto {

    private Long id;

    private double duration;
    private LocalDateTime startDateTime;
    private ResourceDto resource;
    private String location;
    private CourseDto course;
    private TrainerDto trainer;
    private Set<SubjectDto> subjects;

}
