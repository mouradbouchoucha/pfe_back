package com.mrd.server.models;

import com.mrd.server.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String title;
    private String message;
    private LocalDateTime timestamp;
    private String type;
    private boolean isRead;

    public NotificationDto getDto(){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(id);
        notificationDto.setTitle(title);
        notificationDto.setMessage(message);
        notificationDto.setTimestamp(timestamp);
        notificationDto.setType(type);
        notificationDto.setRead(isRead);
        return notificationDto;
    }
}
