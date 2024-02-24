package com.example.webfluxjpa.controller;

import com.example.webfluxjpa.dto.EmployeeDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
        Employee employee = Employee.builder()
                .empId(1)
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();
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
        int empId = 1; // Replace with an actual employee ID
        Employee employee = Employee.builder()
                .empId(empId)
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();
        when(employeeService.findById(1)).thenReturn(Mono.just(employee));

        webTestClient.get().uri("/employees/{empId}", empId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class);
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDto dto =  EmployeeDto.builder()
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();

        Employee employee = Employee.builder()
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();
        when(employeeService.save(dto)).thenReturn(Mono.just(employee));
        webTestClient.post().uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectBody(Employee.class);
    }

    @Test
    public void testUpdateEmployee() {
        int empId = 1; // Replace with an actual employee ID
        EmployeeDto dto =  EmployeeDto.builder()
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();

        Employee employee = Employee.builder()
                .name("Test Employee")
                .age(25)
                .email("a@a.com").build();
        when(employeeService.update(empId,dto)).thenReturn(Mono.just(employee));
        webTestClient.patch().uri("/employees/{empId}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class);
    }

    @Test
    public void testDeleteEmployee() {
        int empId = 1; // Replace with an actual employee ID
        Mockito.doNothing().when(employeeService).delete(empId);
        webTestClient.delete().uri("/employees/{empId}", empId)
                .exchange()
                .expectStatus().isOk();
    }
}
