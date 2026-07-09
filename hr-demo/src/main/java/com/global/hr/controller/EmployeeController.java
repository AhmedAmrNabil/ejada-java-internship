package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.entity.Employee;
import com.global.hr.repository.EmployeeRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	public Page<Employee> getEmployees() {
		log.info("/employees endpoint called");
		return employeeRepository.findAll(Pageable.unpaged());
	}

	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable long id) {
		log.info("/employees/{} endpoint called", id);
		return employeeRepository.findById(id).get();
	}

	@GetMapping("/search/{firstName}")
	public List<Employee> getEmployeesByFirstName(@PathVariable String firstName) {
		log.info("/employees/search/{} endpoint called", firstName);
		return employeeRepository.findByFirstName(firstName);
	}

	@PostMapping
	public String postEmployee(@RequestBody Employee employee) {
		log.info("/employees endpoint called with employee: {}", employee);
		employeeRepository.save(employee);
		return "Employee added successfully";
	}

}
