package com.example.webfluxjpa.controller;

import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    Flux<Employee> getAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{empId}")
    Mono<Employee> getById(@PathVariable Integer empId) {
        return employeeService.findById(empId);
    }

    @PostMapping
    Mono<Employee> save(@Valid @RequestBody EmployeeDto dto) {
        return employeeService.save(dto);
    }

    @PatchMapping("/{empId}")
    Mono<Employee> update(@PathVariable Integer empId,
                          @Valid @RequestBody EmployeeDto dto){
        return employeeService.update(empId, dto);
    }

    @DeleteMapping("/{empId}")
    void delete(@PathVariable Integer empId) {
        employeeService.delete(empId);
    }

}
