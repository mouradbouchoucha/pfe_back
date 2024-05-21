package com.mrd.server.dto;

import lombok.Data;

@Data
public class ResourceDto {

    private Long id;
    private String name;
    private boolean available;
    private String type;
    private String url;
}
