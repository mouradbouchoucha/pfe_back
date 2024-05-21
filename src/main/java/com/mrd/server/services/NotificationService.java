package com.mrd.server.services;

import com.mrd.server.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    NotificationDto createNotification(NotificationDto notificationDTO);

    NotificationDto getNotificationById(Long id);

    List<NotificationDto> getAllNotifications();

    void deleteNotification(Long id);

    NotificationDto updateNotification(Long id, NotificationDto notificationDTO);


}
