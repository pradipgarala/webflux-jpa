package com.example.webfluxjpa.objectmother;

import com.example.webfluxjpa.dto.DepartmentDto;

public class DepartmentDtoMother {
    public static DepartmentDto.DepartmentDtoBuilder complete() {
        return DepartmentDto.builder()
                .name("account")
                .description("account department");
    }
}
