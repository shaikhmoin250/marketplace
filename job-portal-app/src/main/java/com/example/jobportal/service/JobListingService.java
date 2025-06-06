package com.example.jobportal.service;

import com.example.jobportal.model.JobListing;
import com.example.jobportal.model.User;
import com.example.jobportal.model.JobCategory;
import com.example.jobportal.repository.JobListingRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.repository.JobCategoryRepository;
import com.example.jobportal.dto.JobListingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.security.core.context.SecurityContextHolder; // For getting current user
// import org.springframework.security.core.userdetails.UsernameNotFoundException; // For user not found

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobListingService {

    @Autowired
    private JobListingRepository jobListingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Transactional
    public JobListingDto createJobListing(JobListingDto jobListingDto /*, String currentUsername */ ) throws Exception {
        // In a real app, you'd get the current authenticated user:
        // User user = userRepository.findByUsername(currentUsername)
        //        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));
        // For now, we rely on userId from DTO, assuming it's validated or set correctly.
        User user = userRepository.findById(jobListingDto.getUserId())
                .orElseThrow(() -> new Exception("User not found with id: " + jobListingDto.getUserId()));

        JobCategory category = jobCategoryRepository.findById(jobListingDto.getCategoryId())
                .orElseThrow(() -> new Exception("JobCategory not found with id: " + jobListingDto.getCategoryId()));

        JobListing jobListing = new JobListing();
        jobListing.setTitle(jobListingDto.getTitle());
        jobListing.setDescription(jobListingDto.getDescription());
        jobListing.setLocation(jobListingDto.getLocation());
        jobListing.setUser(user);
        jobListing.setJobCategory(category);
        // jobListing.setPostedDate() is handled by @PrePersist

        JobListing savedJobListing = jobListingRepository.save(jobListing);
        return mapToDto(savedJobListing);
    }

    @Transactional(readOnly = true)
    public List<JobListingDto> getAllJobListings() {
        return jobListingRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<JobListingDto> getJobListingById(Long id) {
        return jobListingRepository.findById(id).map(this::mapToDto);
    }

    @Transactional
    public Optional<JobListingDto> updateJobListing(Long id, JobListingDto jobListingDto /*, String currentUsername */) throws Exception {
        Optional<JobListing> existingJobOpt = jobListingRepository.findById(id);
        if (!existingJobOpt.isPresent()) {
            return Optional.empty();
        }
        JobListing existingJob = existingJobOpt.get();

        // Add authorization check: ensure currentUsername matches existingJob.getUser().getUsername() or has ADMIN role

        User user = userRepository.findById(jobListingDto.getUserId())
                .orElseThrow(() -> new Exception("User not found with id: " + jobListingDto.getUserId()));
        JobCategory category = jobCategoryRepository.findById(jobListingDto.getCategoryId())
                .orElseThrow(() -> new Exception("JobCategory not found with id: " + jobListingDto.getCategoryId()));

        existingJob.setTitle(jobListingDto.getTitle());
        existingJob.setDescription(jobListingDto.getDescription());
        existingJob.setLocation(jobListingDto.getLocation());
        existingJob.setUser(user); // Potentially allow changing the user if admin
        existingJob.setJobCategory(category);
        // existingJob.setPostedDate(LocalDateTime.now()); // Or keep original postedDate

        JobListing updatedJob = jobListingRepository.save(existingJob);
        return Optional.of(mapToDto(updatedJob));
    }

    @Transactional
    public boolean deleteJobListing(Long id /*, String currentUsername */) {
        Optional<JobListing> jobOpt = jobListingRepository.findById(id);
        if (jobOpt.isPresent()) {
            // Add authorization check here
            jobListingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper method to map Entity to DTO
    private JobListingDto mapToDto(JobListing jobListing) {
        JobListingDto dto = new JobListingDto();
        dto.setId(jobListing.getId());
        dto.setTitle(jobListing.getTitle());
        dto.setDescription(jobListing.getDescription());
        dto.setLocation(jobListing.getLocation());
        dto.setPostedDate(jobListing.getPostedDate());
        if (jobListing.getUser() != null) {
            dto.setUserId(jobListing.getUser().getId());
            dto.setUsername(jobListing.getUser().getUsername());
        }
        if (jobListing.getJobCategory() != null) {
            dto.setCategoryId(jobListing.getJobCategory().getId());
            dto.setCategoryName(jobListing.getJobCategory().getName());
        }
        return dto;
    }
}
