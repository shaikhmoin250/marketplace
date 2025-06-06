package com.example.jobportal.controller;

import com.example.jobportal.dto.NotificationDto;
import com.example.jobportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getMyNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            // Should not happen if endpoint is secured properly
            return ResponseEntity.status(401).build();
        }
        String username = userDetails.getUsername();
        List<NotificationDto> notifications = notificationService.getNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getMyUnreadNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        String username = userDetails.getUsername();
        List<NotificationDto> notifications = notificationService.getUnreadNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        String username = userDetails.getUsername();
        try {
            return notificationService.markAsRead(notificationId, username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage()); // Forbidden
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Example: Endpoint for admin to create a notification for a user (not in original plan but shows usage)
    // @PostMapping("/admin/create")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<NotificationDto> createNotificationForUser(@RequestParam String username, @RequestParam String message) {
    //     NotificationDto notification = notificationService.createNotificationForUser(username, message);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    // }
}
