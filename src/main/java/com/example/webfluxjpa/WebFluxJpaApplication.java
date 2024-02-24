package com.example.webfluxjpa;

import com.example.webfluxjpa.entity.Department;
import com.example.webfluxjpa.entity.Employee;
import com.example.webfluxjpa.repository.DepartmentRepository;
import com.example.webfluxjpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@RequiredArgsConstructor
public class WebFluxJpaApplication implements CommandLineRunner {

	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(WebFluxJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		departmentRepository.save(Department.builder().name("account").description("account department").build());
		departmentRepository.save(Department.builder().name("engineering").description("engineering department").build());
		departmentRepository.save(Department.builder().name("hr").description("hr department").build());

		employeeRepository.save(Employee.builder().name("alice").age(10).email("alice@test.com").departments(Collections.singletonList(departmentRepository.findById(1).get())).build());
		employeeRepository.save(Employee.builder().name("bob").age(20).email("bob@test.com").departments(Collections.singletonList(departmentRepository.findById(2).get())).build());
		employeeRepository.save(Employee.builder().name("charlie").age(20).email("charlie@test.com").departments(departmentRepository.findAllById(Arrays.asList(1, 2))).build());
	}
}
