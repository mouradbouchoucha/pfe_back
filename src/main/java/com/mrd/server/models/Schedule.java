package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
@ToString(exclude = "course")

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
    private double duration;  // Changed to double
    private LocalDateTime endDateTime;
    private String location;

    @OneToOne(fetch = FetchType.EAGER)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    @JsonManagedReference
    private Trainer trainer;

    // Constructors, getters, and setters

    public Schedule(LocalDateTime startDateTime, double duration) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.endDateTime = calculateEndDateTime();
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = calculateEndDateTime();  // Update endDateTime whenever startDateTime is set
    }

    public void setDuration(double duration) {
        this.duration = duration;
        this.endDateTime = calculateEndDateTime();  // Update endDateTime whenever duration is set
    }

    private LocalDateTime calculateEndDateTime() {
        if (startDateTime != null) {
            long minutes = (long) (duration * 60);  // Convert hours to minutes
            return startDateTime.plusMinutes(minutes);
        } else {
            return null;
        }
    }

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
