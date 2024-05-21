package com.mrd.server.services.impl;

import com.mrd.server.dto.NotificationDto;
import com.mrd.server.models.Notification;
import com.mrd.server.repositories.NotificationRepository;
import com.mrd.server.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDTO) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationDTO, notification);
        return notificationRepository.save(notification).getDto();
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        return Objects.requireNonNull(notificationRepository.findById(id).orElse(null)).getDto();
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDTO) {
        Notification existingNotification = notificationRepository.findById(id).orElse(null);
        if (existingNotification == null) {
            return null; // or throw an exception
        }
        BeanUtils.copyProperties(notificationDTO, existingNotification);
        return convertToDTO(notificationRepository.save(existingNotification));
    }

    private NotificationDto convertToDTO(Notification notification) {
        NotificationDto notificationDTO = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDTO);
        return notificationDTO;
    }
}
