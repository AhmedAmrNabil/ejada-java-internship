package com.global.hr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.global.hr.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,JpaSpecificationExecutor<Employee> {
	List<Employee> findByFirstName(String firstName);
	Page<Employee> findByDepartmentId(long departmentId,Pageable pageable);
}