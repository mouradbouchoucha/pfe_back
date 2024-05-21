package com.mrd.server.models;

import com.mrd.server.dto.TraineeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
public class Trainee {

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

    public TraineeDto getDto(){
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setId(id);
        traineeDto.setFirstName(firstName);
        traineeDto.setLastName(lastName);
        traineeDto.setEmail(email);
        traineeDto.setPhoneNumber(phoneNumber);
        traineeDto.setAddress(address);
        traineeDto.setCity(city);
        traineeDto.setProfilePicture(profilePicture);
        return traineeDto;
    }
}
