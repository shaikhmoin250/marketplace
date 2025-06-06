package com.example.jobportal.service;

import com.example.jobportal.model.Notification;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.NotificationRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.dto.NotificationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository; // To fetch User objects

    @Transactional
    public NotificationDto createNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        // timestamp and isRead have defaults or are set by @PrePersist / default value

        Notification savedNotification = notificationRepository.save(notification);
        return mapToDto(savedNotification);
    }

    @Transactional
    public NotificationDto createNotificationForUser(String username, String message) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);

        Notification savedNotification = notificationRepository.save(notification);
        return mapToDto(savedNotification);
    }


    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return notificationRepository.findByUserOrderByTimestampDesc(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotificationsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return notificationRepository.findByUserAndIsReadOrderByTimestampDesc(user, false).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<NotificationDto> markAsRead(Long notificationId, String username) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + notificationId));

        // Authorization check: Ensure the notification belongs to the current user
        if (!notification.getUser().getUsername().equals(username)) {
            // Or throw an access denied exception
            throw new SecurityException("User not authorized to mark this notification as read.");
        }

        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        return Optional.of(mapToDto(updatedNotification));
    }

    private NotificationDto mapToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
            dto.setUsername(notification.getUser().getUsername()); // Helpful for admin views maybe
        }
        dto.setMessage(notification.getMessage());
        dto.setTimestamp(notification.getTimestamp());
        dto.setRead(notification.isRead());
        return dto;
    }
}
