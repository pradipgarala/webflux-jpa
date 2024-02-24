package com.example.webfluxjpa.service;


import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.objectmother.DepartmentMother;
import com.example.webfluxjpa.objectmother.EmployeeDtoMother;
import com.example.webfluxjpa.objectmother.EmployeeMother;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Employee employee = EmployeeMother.complete().build();
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        Flux<Employee> employeeFlux = employeeService.findAll();

        StepVerifier.create(employeeFlux)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Employee employee = EmployeeMother.complete().build();
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Mono<Employee> employeeMono = employeeService.findById(id);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testSave() {
        EmployeeDto employeeDto = EmployeeDtoMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(departmentRepository.findAllById(any())).thenReturn(Collections.singletonList(DepartmentMother.complete().build()));
        when(modelMapper.map(any(), any())).thenReturn(employee);

        Mono<Employee> employeeMono = employeeService.save(employeeDto);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testSaveThrowsDepartNotFoundException() {
        EmployeeDto employeeDto = EmployeeDtoMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        when(departmentRepository.findAllById(any())).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(), any())).thenReturn(employee);

        assertThatThrownBy(() -> employeeService.save(employeeDto))
                .hasMessage("Department(s) not found");
    }

    @Test
    void testUpdate() {
        EmployeeDto employeeDto = EmployeeDtoMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(departmentRepository.findAllById(any())).thenReturn(Collections.singletonList(DepartmentMother.complete().build()));

        Mono<Employee> employeeMono = employeeService.update(id, employeeDto);

        StepVerifier.create(employeeMono)
                .expectNext(employee)
                .verifyComplete();
    }

    @Test
    void testUpdateThrowsEmployeeNotFoundException() {
        EmployeeDto employeeDto = EmployeeDtoMother.complete().build();
        int id = 1;

        assertThatThrownBy(() -> employeeService.update(id, employeeDto))
                .hasMessage("Employee not found");
    }

    @Test
    void testDelete() {
        int id = 1;

        employeeService.delete(id);

        verify(employeeRepository).deleteById(id);
    }
}
