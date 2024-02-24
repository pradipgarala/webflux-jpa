package com.example.webfluxjpa.controller;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.objectmother.EmployeeDtoMother;
import com.example.webfluxjpa.objectmother.EmployeeMother;
import com.example.webfluxjpa.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllEmployees() {
        Employee employee = EmployeeMother.complete().build();
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        Flux<Employee> employeesFlux = Flux.fromIterable(list);
        when(employeeService.findAll()).thenReturn(employeesFlux);
        webTestClient.get().uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Employee.class);
    }

    @Test
    public void testGetEmployeeById() {
        int empId = 1;
        Employee employee = EmployeeMother.complete().build();
        when(employeeService.findById(1)).thenReturn(Mono.just(employee));

        webTestClient.get().uri("/employees/{empId}", empId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class);
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDto dto = EmployeeDtoMother.complete().build();
        Employee employee = EmployeeMother.complete().build();

        when(employeeService.save(dto)).thenReturn(Mono.just(employee));
        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectBody(Employee.class);
    }

    @Test
    void testSaveEmployeeWhenNameIsBlank() {
        EmployeeDto invalidDto =  EmployeeDto.builder()
                .age(25)
                .email("a@a.com").build();

        webTestClient.post()
                .uri("/employees")
                .body(Mono.just(invalidDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("name").isEqualTo("must not be blank");
    }

    @Test
    void testSaveEmployeeWhenNameLengthIsGreatherThan255() {
        EmployeeDto invalidDto =  EmployeeDto.builder()
                .name("lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum")
                .age(90)
                .email("walter@test.com").build();

        webTestClient.post()
                .uri("/employees")
                .body(Mono.just(invalidDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("name").isEqualTo("Employee name should be less than 255 characters.");
    }

    @Test
    void testSaveEmployeeWhenEmailIsNotValid() {
        EmployeeDto invalidDto =  EmployeeDto.builder()
                .name("walter")
                .age(90)
                .email("waltertestcom").build();

        webTestClient.post()
                .uri("/employees")
                .body(Mono.just(invalidDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("email").isEqualTo("must be a well-formed email address");
    }

    @Test
    public void testUpdateEmployee() {
        int empId = 1;
        EmployeeDto dto = EmployeeDtoMother.complete().build();
        Employee employee = EmployeeMother.complete().build();

        when(employeeService.update(empId, dto)).thenReturn(Mono.just(employee));
        webTestClient.patch().uri("/employees/{empId}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class);
    }

    @Test
    public void testDeleteEmployee() {
        int empId = 1;
        Mockito.doNothing().when(employeeService).delete(empId);
        webTestClient.delete().uri("/employees/{empId}", empId)
                .exchange()
                .expectStatus().isOk();
    }
}
