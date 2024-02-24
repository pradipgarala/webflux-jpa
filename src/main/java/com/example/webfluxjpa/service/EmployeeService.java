package com.example.webfluxjpa.service;

import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.exception.ResourceNotFoundException;
import com.example.webfluxjpa.repository.DepartmentRepository;
import com.example.webfluxjpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private static final String EMPLOYEE_NOT_FOUND = "Employee not found";

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final Scheduler jdbcScheduler;

    public Flux<Employee> findAll() {
        return Flux.defer(() -> Flux.fromIterable(employeeRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Mono<Employee> findById(Integer id) {
        return Mono.fromCallable(() -> employeeRepository.findById(id))
                .flatMap(Mono::justOrEmpty);
    }

    public Mono<Employee> save(EmployeeDto dto) {
        Employee entity = modelMapper.map(dto, Employee.class);
        validateDepartment(dto, entity);
        return save(entity);
    }


    public Mono<Employee> update(Integer id, EmployeeDto dto) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));

        modelMapper.map(dto, entity);
        validateDepartment(dto, entity);

        return save(entity);
    }

    private void validateDepartment(EmployeeDto dto, Employee entity) {
        List<Department> departmentList = Optional.ofNullable(dto.getDepartments())
                .map(departmentRepository::findAllById)
                .orElseGet(Arrays::asList);

        Integer inputSize = Optional.ofNullable(dto.getDepartments())
                .map(Set::size)
                .orElseGet(() -> 0);

        if (inputSize != departmentList.size()) {
            throw new ResourceNotFoundException("Department(s) not found");
        }

        entity.setDepartments(departmentList);
    }

    private Mono<Employee> save(Employee entity) {
        return Mono.defer(() -> Mono.just(employeeRepository.save(entity)))
                .subscribeOn(jdbcScheduler);
    }

    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
}
