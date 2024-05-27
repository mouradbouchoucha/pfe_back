package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.TrainerDto;
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
@Table(name = "trainers")
public class Trainer extends User {

    private String institutionName;
    private String departmentName;
    private Integer yearsOfExperience;
    private String degree;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String city;

    @Lob
    @Column(columnDefinition = "LONGBLOB", length = 1000000000)
    private byte[] profilePicture;

    @OneToMany(mappedBy = "trainer")
    @JsonManagedReference
    private List<Schedule> schedules;

    public TrainerDto getDto(){
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setId(getId());
        trainerDto.setFirstName(getFirstName());
        trainerDto.setLastName(getLastName());
        trainerDto.setEmail(getEmail());
        trainerDto.setInstitutionName(institutionName);
        trainerDto.setDepartmentName(departmentName);
        trainerDto.setYearsOfExperience(yearsOfExperience);
        trainerDto.setDegree(degree);

        trainerDto.setPhoneNumber(phoneNumber);
        trainerDto.setAddress(address);
        trainerDto.setCity(city);
        trainerDto.setProfilePicture(profilePicture);
        return trainerDto;
    }
}
