package com.example.webfluxjpa.service;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.exception.ResourceFoundException;
import com.example.webfluxjpa.repository.DepartmentRepository;
import com.example.webfluxjpa.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

//    @Mock
//    private VirtualTimeScheduler virtualTimeScheduler;

    @Mock
    private Scheduler jdbcScheduler;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartmentService departmentService;

//    @BeforeEach
//    void setUp() {
//        VirtualTimeScheduler.set(virtualTimeScheduler);
//    }

    @Test
    void testFindAll() {
        Department department = getDepartment();
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        Flux<Department> departmentFlux = departmentService.findAll();

        StepVerifier.create(departmentFlux)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Department department = getDepartment();
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(java.util.Optional.of(department));

        Mono<Department> departmentMono = departmentService.findById(id);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testSave() {
        DepartmentDto departmentDto = getDepartmentDto();
        Department department = getDepartment();
        when(departmentRepository.save(department)).thenReturn(department);

        Mono<Department> departmentMono = departmentService.save(departmentDto);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testUpdate() {
        DepartmentDto departmentDto = getDepartmentDto();
        Department department = getDepartment();
        int id = 1;
        when(departmentRepository.findById(id)).thenReturn(java.util.Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Mono<Department> departmentMono = departmentService.update(id, departmentDto);

        StepVerifier.create(departmentMono)
                .expectNext(department)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        int id = 1;
        when(employeeRepository.findByDepartmentsDepId(id)).thenReturn(Collections.emptyList());

        departmentService.delete(id);

        verify(departmentRepository).deleteById(id);
    }

    @Test
    void testDeleteWithEmployees() {
        int id = 1;
        when(employeeRepository.findByDepartmentsDepId(id)).thenReturn(Collections.singletonList(new Employee()));

        assertThrows(ResourceFoundException.class, () -> departmentService.delete(id));

        verify(departmentRepository, never()).deleteById(anyInt());
    }

    private Department getDepartment() {
        Department entity = new Department();
        entity.setDepId(1);
        entity.setName("test");
        entity.setDescription("desc");

        return entity;
    }

    private DepartmentDto getDepartmentDto() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("test");
        dto.setDescription("desc");

        return dto;
    }
}
