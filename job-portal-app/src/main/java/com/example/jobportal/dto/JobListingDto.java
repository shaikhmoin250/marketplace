package com.example.jobportal.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class JobListingDto {
    private Long id;

    @NotEmpty(message = "Job title cannot be empty")
    @Size(min = 5, max = 150, message = "Job title must be between 5 and 150 characters")
    private String title;

    @NotEmpty(message = "Job description cannot be empty")
    private String description;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    private LocalDateTime postedDate;

    @NotNull(message = "User ID cannot be null for a job posting")
    private Long userId; // For creating/updating, refer to user by ID
    private String username; // For displaying username in response

    @NotNull(message = "Category ID cannot be null for a job posting")
    private Long categoryId; // For creating/updating
    private String categoryName; // For displaying category name in response

    // private String companyName;
    // private LocalDateTime applicationDeadline;
    // private String attachedFilePath;
}
