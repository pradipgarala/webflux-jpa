package com.example.webfluxjpa.objectmother;

import com.example.webfluxjpa.dto.EmployeeDto;

import java.util.Collections;
import java.util.HashSet;

public class EmployeeDtoMother {
    public static EmployeeDto.EmployeeDtoBuilder complete() {
        return EmployeeDto.builder()
                .name("alice")
                .age(10)
                .email("alice@test.com")
                .departments(new HashSet<>(Collections.singletonList(1)));
    }
}
