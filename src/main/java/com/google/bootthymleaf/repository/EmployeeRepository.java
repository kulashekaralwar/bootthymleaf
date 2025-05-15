package com.google.bootthymleaf.repository;

import com.google.bootthymleaf.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmail(String email); // Used for login
}
