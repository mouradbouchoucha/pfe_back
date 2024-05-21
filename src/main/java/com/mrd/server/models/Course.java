package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.CourseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(columnDefinition = "LONGBLOB",length = 1000000000)
    private byte[] image;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public CourseDto getDto(){
        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setDescription(description);
        courseDto.setDuration(duration);
        courseDto.setStartDateTime(startDateTime);
        courseDto.setImage(image);
        courseDto.setCategory_id(category.getId());
        courseDto.setCategoryName(category.getName());
        return courseDto;
    }
}
