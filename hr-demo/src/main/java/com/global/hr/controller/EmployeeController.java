package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.entity.Employee;
import com.global.hr.service.EmployeeService;

import java.net.URI;
import java.util.List;

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

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getEmployees() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}

	@GetMapping("/search/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeesByFirstName(@PathVariable String firstName) {
		return ResponseEntity.ok(employeeService.findByFirstName(firstName));
	}

	@PostMapping
	public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {

		Employee savedEmployee = employeeService.addEmployee(employee);
		URI location = URI.create("/employees/" + savedEmployee.getId());

		return ResponseEntity
				.created(location)
				.body(savedEmployee);
	}

	@PutMapping
	public ResponseEntity<Employee> putEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.updateEmployee(employee));
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employeeService.deleteEmployeeById(id);
	}

}
