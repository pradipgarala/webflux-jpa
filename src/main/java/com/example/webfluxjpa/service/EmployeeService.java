package com.example.webfluxjpa.service;

import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.exception.ResourceNotFoundException;
import com.example.webfluxjpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final String EMPLOYEE_NOT_FOUND = "Employee not found";

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final Scheduler jdbcScheduler;

    public Flux<Employee> findAll() {
        return Flux.defer(() -> Flux.fromIterable(employeeRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Mono<Employee> findById(Integer id) {
        return Mono.defer(() -> Mono.just(employeeRepository.findById(id)))
                .subscribeOn(jdbcScheduler)
                .flatMap(o -> o.<Mono<? extends Employee>>map(Mono::just).orElseGet(Mono::empty));
    }

    public Mono<Employee> save(EmployeeDto dto) {
        Employee entity = modelMapper.map(dto, Employee.class);
        return save(entity);
    }

    public Mono<Employee> update(Integer id, EmployeeDto dto) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));

        modelMapper.map(dto, entity);

        return save(entity);
    }

    private Mono<Employee> save(Employee entity) {
        return Mono.defer(() -> Mono.just(employeeRepository.save(entity)))
                .subscribeOn(jdbcScheduler);
    }

    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
}
