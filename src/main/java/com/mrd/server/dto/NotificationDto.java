package com.mrd.server.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;
    private Long user_id;
    private String title;
    private String message;
    private String type;
    private Timestamp createdAt;
    private boolean read;
}
