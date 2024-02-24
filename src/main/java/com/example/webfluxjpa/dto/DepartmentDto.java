package com.example.webfluxjpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {
    @NotBlank
    @Size(min = 1, max = 255, message = "Department name should be less than 255 characters.")
    private String name;
    private String description;
}