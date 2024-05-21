package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.TrainerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;

    @Lob
    @Column(columnDefinition = "LONGBLOB",length = 1000000000)
    private byte[] profilePicture;

    @OneToMany(mappedBy = "trainer")
    @JsonManagedReference
    private List<Schedule> schedules;

    public TrainerDto getDto(){
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setId(id);
        trainerDto.setFirstName(firstName);
        trainerDto.setLastName(lastName);
        trainerDto.setEmail(email);
        trainerDto.setPhoneNumber(phoneNumber);
        trainerDto.setAddress(address);
        trainerDto.setCity(city);
        trainerDto.setProfilePicture(profilePicture);
        return trainerDto;
    }
}
