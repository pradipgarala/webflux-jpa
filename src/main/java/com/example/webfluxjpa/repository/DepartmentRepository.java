package com.example.webfluxjpa.repository;

import com.example.webfluxjpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
