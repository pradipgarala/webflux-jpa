package com.example.webfluxjpa.service;


import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.exception.ResourceNotFoundException;
import com.example.webfluxjpa.repository.DepartmentRepository;
import com.example.webfluxjpa.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testFindAll() {
        Employee employee = getEmployee();
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        Flux<Employee> employeeFlux = employeeService.findAll();

        StepVerifier.create(employeeFlux)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Employee employee = getEmployee();
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Mono<Employee> employeeMono = employeeService.findById(id);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testSave() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(departmentRepository.findAllById(any())).thenReturn(Collections.singletonList(getDepartment()));
        when(modelMapper.map(any(), any())).thenReturn(getEmployee());

        Mono<Employee> employeeMono = employeeService.save(employeeDto);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testUpdate() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(departmentRepository.findAllById(any())).thenReturn(Collections.singletonList(getDepartment()));

        Mono<Employee> employeeMono = employeeService.update(id, employeeDto);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        int id = 1;

        employeeService.delete(id);

        verify(employeeRepository).deleteById(id);
    }

    private Employee getEmployee() {
        Employee entity = new Employee();
        entity.setEmpId(1);
        entity.setName("test");
        entity.setAge(10);
        entity.setEmail("test@test.com");
        entity.setDepartments(Collections.singletonList(getDepartment()));

        return entity;
    }

    private Department getDepartment() {
        return new Department(1, "test", "desc");
    }

    private EmployeeDto getEmployeeDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setName("test");
        dto.setAge(10);
        dto.setEmail("test@test.com");

        Set<Integer> departments = new HashSet<>();
        departments.add(1);

        dto.setDepartments(departments);

        return dto;
    }
}
