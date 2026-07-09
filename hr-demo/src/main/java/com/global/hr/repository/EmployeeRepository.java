package com.global.hr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import com.global.hr.entity.Employee;

public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {
	List<Employee> findByFirstName(String firstName);

	Page<Employee> findAll(Pageable pageable);
}