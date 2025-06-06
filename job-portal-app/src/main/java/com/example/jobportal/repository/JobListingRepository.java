package com.example.jobportal.repository;

import com.example.jobportal.model.JobListing;
import com.example.jobportal.model.JobCategory;
import com.example.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobListingRepository extends JpaRepository<JobListing, Long> {
    List<JobListing> findByJobCategory(JobCategory jobCategory);
    List<JobListing> findByUser(User user);
    List<JobListing> findByTitleContainingIgnoreCase(String title);
    List<JobListing> findByLocationContainingIgnoreCase(String location);
}
