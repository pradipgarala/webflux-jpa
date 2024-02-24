package com.example.webfluxjpa.service;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.exception.ResourceFoundException;
import com.example.webfluxjpa.exception.ResourceNotFoundException;
import com.example.webfluxjpa.repository.DepartmentRepository;
import com.example.webfluxjpa.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    private static final String DEPARTMENT_NOT_FOUND = "Department not found";
    private static final String EMPLOYEE_EXIST = "Department can't be deleted, Employee exist";

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Flux<Department> findAll() {
        return Flux.defer(() -> Flux.fromIterable(departmentRepository.findAll()));
    }

    public Mono<Department> findById(Integer id) {
        return Mono.fromCallable(() -> departmentRepository.findById(id))
                .flatMap(Mono::justOrEmpty);
    }

    public Mono<Department> save(DepartmentDto dto) {
        Department entity = modelMapper.map(dto, Department.class);
        return save(entity);
    }

    public Mono<Department> update(Integer id, DepartmentDto dto) {
        Department entity = getEntityOrThrowException(id);

        modelMapper.map(dto, entity);

        return save(entity);
    }

    private Mono<Department> save(Department entity) {
        return Mono.fromCallable(() -> departmentRepository.save(entity));
    }

    public void delete(Integer id) {
        getEntityOrThrowException(id);

        employeeRepository.findByDepartmentsDepId(id).stream()
                .findAny()
                .ifPresent(e -> {
                    throw new ResourceFoundException(EMPLOYEE_EXIST);
                });

        departmentRepository.deleteById(id);
    }

    private Department getEntityOrThrowException(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }
}
