package com.example.jobportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Job title cannot be empty")
    @Size(min = 5, max = 150, message = "Job title must be between 5 and 150 characters")
    private String title;

    @Lob // For potentially long descriptions
    @Column(nullable = false)
    @NotEmpty(message = "Job description cannot be empty")
    private String description;

    @Column(nullable = false)
    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @Column(name = "posted_date", nullable = false)
    private LocalDateTime postedDate;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY is often better for performance
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Job listing must have a poster (user)")
    private User user; // The user who posted the job

    @ManyToOne(fetch = FetchType.EAGER) // EAGER might be fine if category is always needed
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Job listing must have a category")
    private JobCategory jobCategory;

    // Optional: field for company name if not part of User
    // private String companyName;

    // Optional: field for application deadline
    // private LocalDateTime applicationDeadline;

    // Optional: field for file path if a document is associated (e.g. detailed description PDF)
    // private String attachedFilePath;

    @PrePersist
    protected void onCreate() {
        postedDate = LocalDateTime.now();
    }
}
