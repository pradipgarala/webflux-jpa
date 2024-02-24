package com.example.webfluxjpa.service;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.exception.ResourceFoundException;
import com.example.webfluxjpa.objectmother.DepartmentDtoMother;
import com.example.webfluxjpa.objectmother.DepartmentMother;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testFindAll() {
        Department department = DepartmentMother.complete().build();
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        Flux<Department> departmentFlux = departmentService.findAll();

        StepVerifier.create(departmentFlux)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Department department = DepartmentMother.complete().build();
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        Mono<Department> departmentMono = departmentService.findById(id);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testSave() {
        DepartmentDto departmentDto = DepartmentDtoMother.complete().build();
        Department department = DepartmentMother.complete().build();
        when(departmentRepository.save(department)).thenReturn(department);
        when(modelMapper.map(any(), any())).thenReturn(department);

        Mono<Department> departmentMono = departmentService.save(departmentDto);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testUpdate() {
        DepartmentDto departmentDto = DepartmentDtoMother.complete().build();
        Department department = DepartmentMother.complete().build();
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Mono<Department> departmentMono = departmentService.update(id, departmentDto);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testUpdateThrowsException() {
        DepartmentDto departmentDto = DepartmentDtoMother.complete().build();
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.update(id, departmentDto))
                .hasMessage("Department not found");
    }

    @Test
    void testDelete() {
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(DepartmentMother.complete().build()));
        when(employeeRepository.findByDepartmentsDepId(id)).thenReturn(Collections.emptyList());

        departmentService.delete(id);

        verify(departmentRepository).deleteById(id);
    }

    @Test
    void testDeleteWithEmployees() {
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(DepartmentMother.complete().build()));
        when(employeeRepository.findByDepartmentsDepId(id)).thenReturn(Collections.singletonList(new Employee()));

        assertThrows(ResourceFoundException.class, () -> departmentService.delete(id));

        verify(departmentRepository, never()).deleteById(anyInt());
    }
}
