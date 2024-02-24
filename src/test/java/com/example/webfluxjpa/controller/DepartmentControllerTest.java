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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebFluxTest(DepartmentController.class)
public class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private WebTestClient webTestClient;

    private final Validator validator = new LocalValidatorFactoryBean();

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
    void testSaveDepartmentWhenNameIsBlank() {
        DepartmentDto invalidDto = DepartmentDto.builder()
                .name("")
                .description("Marketing")
                .build();

        webTestClient.post()
                .uri("/departments")
                .body(Mono.just(invalidDto), DepartmentDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("name").isEqualTo("must not be blank");
    }

    @Test
    void testSaveDepartmentWhenNameLengthIsGreatherThan255() {
        DepartmentDto invalidDto = DepartmentDto.builder()
                .name("lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec velit.")
                .build();

        webTestClient.post()
                .uri("/departments")
                .body(Mono.just(invalidDto), DepartmentDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("name").isEqualTo("Department name should be less than 255 characters.");
    }
    @Test
    void testSaveDepartmentWhenDescriptionLengthIsGreatherThan255() {
        DepartmentDto invalidDto = DepartmentDto.builder()
                .name("Test Department")
                .description("lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum")
                .build();

        webTestClient.post()
                .uri("/departments")
                .body(Mono.just(invalidDto), DepartmentDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("description").isEqualTo("Description should be less than 255 characters.");
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