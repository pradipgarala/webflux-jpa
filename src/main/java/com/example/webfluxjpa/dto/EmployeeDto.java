package com.example.webfluxjpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
    @NotBlank
    @Size(min = 1, max = 255, message = "Employee name should be less than 255 characters.")
    private String name;
    private Integer age;
    @Email
    private String email;
}
