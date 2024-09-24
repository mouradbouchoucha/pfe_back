package com.mrd.server.dto;

import lombok.Data;

@Data
public class CenterDto {

    private Long id;
    private String name;
    private String description;
    private String location;
    private byte[] image;

}
