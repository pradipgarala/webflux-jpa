package com.example.webfluxjpa.controller;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    Flux<Department> getAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{depId}")
    Mono<Department> getById(@PathVariable Integer depId) {
        return departmentService.findById(depId);
    }

    @PostMapping
    Mono<Department> save(@Valid @RequestBody DepartmentDto dto) {
        return departmentService.save(dto);
    }

    @PatchMapping("/{depId}")
    Mono<Department> update(@PathVariable Integer depId,
                            @Valid @RequestBody DepartmentDto dto) {
        return departmentService.update(depId, dto);
    }

    @DeleteMapping("/{depId}")
    void delete(@PathVariable Integer depId) {
        departmentService.delete(depId);
    }

}
