package com.mrd.server.dto;

import com.mrd.server.models.Gender;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TrainerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String institutionName;
    private String departmentName;
    private Integer yearsOfExperience;
    private String degree;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String city;
    private byte[] profilePicture;
    private MultipartFile profilePictureFile;

}
