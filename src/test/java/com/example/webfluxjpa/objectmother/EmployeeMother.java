package com.example.webfluxjpa.objectmother;

import com.example.webfluxjpa.entity.Employee;

import java.util.Collections;
import java.util.HashSet;

public class EmployeeMother {
    public static Employee.EmployeeBuilder complete() {
        return Employee.builder()
                .name("alice")
                .age(10)
                .email("alice@test.com")
                .departments(Collections.singletonList(DepartmentMother.complete().build()));
    }
}
