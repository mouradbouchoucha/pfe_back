package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.mrd.server.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_id", "trainer_id", "start_date_time", "end_date_time"})
})
@ToString(exclude = "course")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = true)
    @JsonBackReference
    private Course course;

//    @OneToOne(fetch = FetchType.EAGER)
//    private Resource resource;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDateTime;

    private double duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDateTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonManagedReference
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    @JsonManagedReference
    private Trainer trainer;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(nullable = false, name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
    public Schedule(LocalDateTime startDateTime, double duration) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.endDateTime = calculateEndDateTime();
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = calculateEndDateTime();  // Update endDateTime whenever startDateTime is set
    }

    @JsonSetter("startDateTime")
    public void setStartDateTime(String startDateTime) {
        // Strip time zone information before parsing
//        String stripped = startDateTime.substring(0, 19);
        this.startDateTime = LocalDateTime.parse(startDateTime);
//                LocalDateTime.parse(stripped);
        this.endDateTime = calculateEndDateTime();
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
        System.out.println(startDateTime);
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(id);
        scheduleDto.setCourse(course.getDto());
        scheduleDto.setStartDateTime(startDateTime);
        scheduleDto.setDuration(duration);
        scheduleDto.setLocation(location);
        scheduleDto.setSubject(subject.getDto());
        scheduleDto.setTrainer(trainer.getDto());
//        scheduleDto.setResource(resource.getDto());
        System.out.println(scheduleDto);
        return scheduleDto;
    }
}
