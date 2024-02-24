package com.example.webfluxjpa.controller;

import com.example.webfluxjpa.dto.DepartmentDto;
import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.objectmother.DepartmentDtoMother;
import com.example.webfluxjpa.objectmother.DepartmentMother;
import com.example.webfluxjpa.service.DepartmentService;
import org.hamcrest.CoreMatchers;
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
@WebFluxTest(DepartmentController.class)
public class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllDepartments() {
        Department department = DepartmentMother.complete().build();
        List<Department> list = new ArrayList<>();
        list.add(department);
        Flux<Department> departmentsFlux = Flux.fromIterable(list);
        when(departmentService.findAll()).thenReturn(departmentsFlux);

        webTestClient.get().uri("/departments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Department.class).value(departments -> departments.get(0).getName(), CoreMatchers.equalTo("account"));
    }

    @Test
    public void testGetDepartmentById() {
        Department department = DepartmentMother.complete().build();
        int depId = 1;
        when(departmentService.findById(depId)).thenReturn(Mono.just(department));

        webTestClient.get().uri("/departments/{depId}", depId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Department.class);
    }

    @Test
    public void testSaveDepartment() {
        Department department = DepartmentMother.complete().build();

        DepartmentDto dto = DepartmentDtoMother.complete().build();

        when(departmentService.save(dto)).thenReturn(Mono.just(department));
        webTestClient.post().uri("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), DepartmentDto.class)
                .exchange()
                .expectBody(Department.class);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = DepartmentMother.complete().build();

        int depId = 1;

        DepartmentDto dto = DepartmentDtoMother.complete().build();

        when(departmentService.update(depId, dto)).thenReturn(Mono.just(department));

        webTestClient.patch().uri("/departments/{depId}", depId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), DepartmentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Department.class);
    }

    @Test
    public void testDeleteDepartment() {
        int depId = 1;
        Mockito.doNothing().when(departmentService).delete(depId);
        webTestClient.delete().uri("/departments/{depId}", depId)
                .exchange()
                .expectStatus().isOk();
    }
}