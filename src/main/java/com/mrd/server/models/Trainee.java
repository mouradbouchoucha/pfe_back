package com.mrd.server.models;

import com.mrd.server.dto.TraineeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
public class Trainee extends User {

    private String profession;
    private String phoneNumber;
    private String address;
    private String city;

    @Lob
    @Column(columnDefinition = "LONGBLOB", length = 1000000000)
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(
            name = "enrolled_courses",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> enrolledCourses;

    @ManyToMany
    @JoinTable(
            name = "liked_courses",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> LikedCourses;

    public TraineeDto getDto(){
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setId(getId());
        traineeDto.setFirstName(getFirstName());
        traineeDto.setLastName(getLastName());
        traineeDto.setEmail(getEmail());
        traineeDto.setProfession(profession);
        traineeDto.setPhoneNumber(phoneNumber);
        traineeDto.setAddress(address);
        traineeDto.setCity(city);
        traineeDto.setProfilePicture(profilePicture);
        return traineeDto;
    }
}
