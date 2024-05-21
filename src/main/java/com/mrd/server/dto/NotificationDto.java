package com.mrd.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;
    private String title;
    private String message;
    private String type;
    private LocalDateTime timestamp;
    private boolean isRead;
}
