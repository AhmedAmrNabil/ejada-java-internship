package com.global.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.global.hr.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByFirstName(String firstName);
}