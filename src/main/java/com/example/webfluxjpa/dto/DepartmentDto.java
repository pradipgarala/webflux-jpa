package com.example.webfluxjpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class DepartmentDto {
    @NotBlank
    @Size(min = 1, max = 255, message = "Department name should be less than 255 characters.")
    private String name;
    private String description;
}