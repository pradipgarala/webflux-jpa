package com.example.webfluxjpa.objectmother;

import com.example.webfluxjpa.entity.Department;

public class DepartmentMother {
    public static Department.DepartmentBuilder complete() {
        return Department.builder()
                .depId(1)
                .name("account")
                .description("account department");
    }
}
