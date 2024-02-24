package com.example.webfluxjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    @NotBlank
    @Size(min = 1, max = 255, message = "Employee name should be less than 255 characters.")
    private String name;
    private Integer age;
    @Email
    private String email;
    private Set<Integer> departments;
}
