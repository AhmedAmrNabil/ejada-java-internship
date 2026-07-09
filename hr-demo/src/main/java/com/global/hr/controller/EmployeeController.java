package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.model.Employee;
import com.global.hr.repository.EmployeeRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeRepository employeeRepository;

	private Logger log = LoggerFactory.getLogger(EmployeeController.class);

	public EmployeeController(@Qualifier("employeeNamedJdbcRepository") EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	public List<Employee> getEmployees() {
		log.info("/employees endpoint called");
		return employeeRepository.findAll();
	}

	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable long id) {
		log.info("/employees/{} endpoint called", id);
		return employeeRepository.findById(id);
	}

	@PostMapping
	public String postEmployee(@RequestBody Employee employee) {
		employeeRepository.insert(employee);
		return "Employee added successfully";
	}

}
