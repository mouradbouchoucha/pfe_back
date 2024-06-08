package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;

    @OneToOne(fetch = FetchType.EAGER)
    private Resource resource;

    private LocalDateTime startDateTime;
    private int duration; // in hours
    private String location;

    @OneToOne(fetch = FetchType.EAGER)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    @JsonManagedReference
    private Trainer trainer;

    public ScheduleDto getDto() {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(id);
        scheduleDto.setCourse(course.getDto());
        scheduleDto.setStartDateTime(startDateTime);
        scheduleDto.setDuration(duration);
        scheduleDto.setLocation(location);
        scheduleDto.setSubject(subject.getDto());
        scheduleDto.setTrainer(trainer.getDto());
        scheduleDto.setResource(resource.getDto());
        return scheduleDto;
    }
}
