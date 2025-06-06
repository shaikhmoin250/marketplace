package com.example.jobportal.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private Long userId; // Keep it simple, just user ID
    private String username; // Optionally, add username if needed in response
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;
}
