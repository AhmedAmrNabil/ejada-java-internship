package com.global.hr.repository;

import java.util.List;

import com.global.hr.model.Employee;

public interface EmployeeRepository {
	Employee findById(long id);
	List<Employee> findAll();
	int count();
	void insert(Employee employee);
	void update(Employee employee);
	void delete(long id);
}