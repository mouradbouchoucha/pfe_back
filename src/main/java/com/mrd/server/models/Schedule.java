package com.mrd.server.models;

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

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonManagedReference
    private Course course;

    @OneToOne
    private Resource resource;



    private LocalDateTime startDateTime;
    private int duration;//in hours
    private String location;

    @OneToOne
    private Subject subject;


    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    @JsonManagedReference
    private Trainer trainer;

    public ScheduleDto getDto(){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(id);
        scheduleDto.setStartDateTime(startDateTime);
        scheduleDto.setDuration(duration);
        scheduleDto.setLocation(location);
        return scheduleDto;
    }

}
