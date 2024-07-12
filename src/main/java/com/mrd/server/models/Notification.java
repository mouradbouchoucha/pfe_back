package com.mrd.server.models;

import com.mrd.server.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
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
    private Timestamp createdAt;
    private String type;
    private boolean is_read;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    public NotificationDto getDto(){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(id);
        notificationDto.setTitle(title);
        notificationDto.setMessage(message);
        notificationDto.setCreatedAt(createdAt);
        notificationDto.setType(type);
        notificationDto.setRead(is_read);
        return notificationDto;
    }
}
