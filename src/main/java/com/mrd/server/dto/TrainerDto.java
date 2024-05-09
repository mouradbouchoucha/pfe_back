package com.mrd.server.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TrainerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private byte[] profilePicture;
    private MultipartFile profilePictureFile;

}
