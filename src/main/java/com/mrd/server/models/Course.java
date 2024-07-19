package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.CourseDto;
import com.mrd.server.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int duration;
    private LocalDateTime startDateTime;

    @Lob
    @Column(columnDefinition = "LONGBLOB", length = 1000000000)
    private byte[] image;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    @JsonBackReference
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "course_trainee_enrollment",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    @JsonBackReference
    private List<Trainee> enrolledTrainees;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<EnrollmentRequest> enrollmentRequests;

    @ManyToMany
    @JoinTable(
            name = "course_trainee_likes",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    @JsonBackReference
    private List<Trainee> likedTrainees;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(nullable = false, name = "updated_at")
    private Timestamp updatedAt;

    @PreRemove
    private void preRemove() {
        for (Schedule s : schedules) {
            s.setCourse(null);
        }
    }
    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public CourseDto getDto() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);
        courseDto.setStartDateTime(startDateTime);
        courseDto.setImage(image);
        if (category != null) {
            courseDto.setCategory_id(category.getId());
            courseDto.setCategoryName(category.getName());
        }
        if (schedules != null) {
            courseDto.setSchedules(convertToScheduleDtoList(schedules));
        }
        return courseDto;
    }

    private List<ScheduleDto> convertToScheduleDtoList(List<Schedule> schedules) {
        return schedules.stream().map(this::convertToScheduleDto).collect(Collectors.toList());
    }

    private ScheduleDto convertToScheduleDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDuration(schedule.getDuration());
        scheduleDto.setLocation(schedule.getLocation());
        scheduleDto.setCourse(convertToCourseDto(schedule.getCourse()));
        scheduleDto.setStartDateTime(schedule.getStartDateTime());
        return scheduleDto;
    }

    private CourseDto convertToCourseDto(Course course) {
        return new CourseDto();
    }
}
