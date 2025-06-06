package com.example.jobportal.service;

import com.example.jobportal.model.JobCategory;
import com.example.jobportal.repository.JobCategoryRepository;
import com.example.jobportal.dto.JobCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Transactional
    public JobCategoryDto createCategory(JobCategoryDto jobCategoryDto) throws Exception {
        if (jobCategoryRepository.existsByName(jobCategoryDto.getName())) {
            throw new Exception("Job category with name '" + jobCategoryDto.getName() + "' already exists.");
        }
        JobCategory jobCategory = new JobCategory();
        jobCategory.setName(jobCategoryDto.getName());
        JobCategory savedCategory = jobCategoryRepository.save(jobCategory);
        return mapToDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<JobCategoryDto> getAllCategories() {
        return jobCategoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<JobCategoryDto> getCategoryById(Long id) {
        return jobCategoryRepository.findById(id).map(this::mapToDto);
    }

    @Transactional
    public Optional<JobCategoryDto> updateCategory(Long id, JobCategoryDto jobCategoryDto) throws Exception {
        Optional<JobCategory> existingCategoryOpt = jobCategoryRepository.findById(id);
        if (!existingCategoryOpt.isPresent()) {
            return Optional.empty();
        }

        // Check if new name conflicts with another existing category
        Optional<JobCategory> categoryByName = jobCategoryRepository.findByName(jobCategoryDto.getName());
        if (categoryByName.isPresent() && !categoryByName.get().getId().equals(id)) {
            throw new Exception("Another job category with name '" + jobCategoryDto.getName() + "' already exists.");
        }

        JobCategory existingCategory = existingCategoryOpt.get();
        existingCategory.setName(jobCategoryDto.getName());
        JobCategory updatedCategory = jobCategoryRepository.save(existingCategory);
        return Optional.of(mapToDto(updatedCategory));
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        if (jobCategoryRepository.existsById(id)) {
            // Consider checking if any JobListing uses this category before deleting
            jobCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private JobCategoryDto mapToDto(JobCategory jobCategory) {
        JobCategoryDto dto = new JobCategoryDto();
        dto.setId(jobCategory.getId());
        dto.setName(jobCategory.getName());
        return dto;
    }
}
