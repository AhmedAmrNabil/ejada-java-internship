package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.entity.Employee;
import com.global.hr.repository.EmployeeRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<Page<Employee>> getEmployees() {
		log.info("/employees endpoint called");
		return ResponseEntity.ok(employeeRepository.findAll(Pageable.unpaged()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		log.info("/employees/{} endpoint called", id);
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return ResponseEntity.ok(employee.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeesByFirstName(@PathVariable String firstName) {
		log.info("GET /employees/search/{} endpoint called", firstName);
		return ResponseEntity.ok(employeeRepository.findByFirstName(firstName));
	}

	@PostMapping
	public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {
		log.info("POST /employees endpoint called with employee: {}", employee);
		Employee savedEmployee = employeeRepository.save(employee);
		URI location = URI.create("/employees/" + savedEmployee.getId());
		return ResponseEntity.created(location).body(savedEmployee);
	}

	@PutMapping
	public Employee putEmployee(@RequestBody Employee employee) {
		log.info("PUT /employees endpoint called with employee: {}", employee);
		return employeeRepository.save(employee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		log.info("DELETE /employees/{} endpoint called", id);
		employeeRepository.deleteById(id);
	}

}
