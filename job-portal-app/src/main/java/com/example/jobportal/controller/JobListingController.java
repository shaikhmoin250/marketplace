package com.example.jobportal.controller;

import com.example.jobportal.dto.JobListingDto;
import com.example.jobportal.service.JobListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal; // To get logged in user details
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobListingController {

    @Autowired
    private JobListingService jobListingService;

    @PostMapping
    public ResponseEntity<?> createJobListing(@Valid @RequestBody JobListingDto jobListingDto /*, @AuthenticationPrincipal UserDetails userDetails */) {
        // String username = userDetails.getUsername(); // Get username of logged-in user
        try {
            // Pass username to service if needed for associating the job with the current user automatically
            JobListingDto createdJob = jobListingService.createJobListing(jobListingDto /*, username */);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<JobListingDto>> getAllJobListings() {
        List<JobListingDto> jobs = jobListingService.getAllJobListings();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobListingDto> getJobListingById(@PathVariable Long id) {
        return jobListingService.getJobListingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobListing(@PathVariable Long id, @Valid @RequestBody JobListingDto jobListingDto /*, @AuthenticationPrincipal UserDetails userDetails */) {
        // String username = userDetails.getUsername();
        try {
            return jobListingService.updateJobListing(id, jobListingDto /*, username */)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobListing(@PathVariable Long id /*, @AuthenticationPrincipal UserDetails userDetails */) {
        // String username = userDetails.getUsername();
        if (jobListingService.deleteJobListing(id /*, username */)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
