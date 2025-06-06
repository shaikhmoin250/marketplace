package com.example.jobportal.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class JobCategoryDto {
    private Long id; // Include ID for responses, but not strictly for creation if auto-generated

    @NotEmpty(message = "Category name cannot be empty")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;
}
