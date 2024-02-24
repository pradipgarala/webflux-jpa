package com.example.webfluxjpa.repository;

import com.example.webfluxjpa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e join e.departments d where d.depId = :depId")
    List<Employee> findByDeptId(@Param("depId")Integer depId);

    List<Employee> findByDepartmentsDepId(Integer depId);
}
