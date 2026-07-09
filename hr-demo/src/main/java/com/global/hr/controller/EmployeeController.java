package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.dto.request.CreateEmployeeRequest;
import com.global.hr.dto.response.ApiError;
import com.global.hr.entity.Employee;
import com.global.hr.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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

@Tag(name = "Employee Management", description = "APIs for managing employees")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Operation(summary = "Get all employees")
	@GetMapping
	public ResponseEntity<List<Employee>> getEmployees() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@Operation(summary = "Get employee by ID")
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}

	@Operation(summary = "Search employees by first name")
	@GetMapping("/search/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeesByFirstName(@PathVariable String firstName) {
		return ResponseEntity.ok(employeeService.findByFirstName(firstName));
	}

	@Operation(summary = "Create a new employee")
	@ApiResponse(responseCode = "422", description = "Validation failed", content = @Content(schema = @Schema(implementation = ApiError.class)))
	@PostMapping
	public ResponseEntity<Employee> postEmployee(@Valid @RequestBody CreateEmployeeRequest employeeRequest) {

		Employee savedEmployee = employeeService.addEmployee(employeeRequest);
		URI location = URI.create("/employees/" + savedEmployee.getId());

		return ResponseEntity
				.created(location)
				.body(savedEmployee);
	}

	@Operation(summary = "Update an existing employee")
	@PutMapping
	public ResponseEntity<Employee> putEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.updateEmployee(employee));
	}

	@Operation(summary = "Delete an employee by ID")
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employeeService.deleteEmployeeById(id);
	}

}
