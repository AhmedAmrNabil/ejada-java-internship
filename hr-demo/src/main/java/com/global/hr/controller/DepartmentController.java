package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;

import com.global.hr.dto.request.CreateDepartmentRequest;
import com.global.hr.dto.response.ApiError;
import com.global.hr.entity.Department;
import com.global.hr.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Department Management", description = "APIs for managing departments")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private DepartmentService departmentService;

	private Logger log = LoggerFactory.getLogger(DepartmentController.class);

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Operation(summary = "Get all departments")
	@GetMapping
	public ResponseEntity<Page<Department>> getDepartments(Pageable pageable) {
		log.info("Fetching departments with pagination: {}", pageable);
		return ResponseEntity.ok(departmentService.findAll(pageable));
	}

	@Operation(summary = "Get department by ID")
	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
		return ResponseEntity.ok(departmentService.findById(id));
	}

	@Operation(summary = "Search departments by name")
	@GetMapping("/search/{name}")
	public ResponseEntity<List<Department>> getDepartmentsByName(@PathVariable String name) {
		return ResponseEntity.ok(departmentService.findByName(name));
	}

	@Operation(summary = "Create a new department")
	@ApiResponse(responseCode = "422", description = "Validation failed", content = @Content(schema = @Schema(implementation = ApiError.class)))
	@PostMapping
	public ResponseEntity<Department> postDepartment(@Valid @RequestBody CreateDepartmentRequest departmentRequest) {

		Department savedDepartment = departmentService.addDepartment(departmentRequest);
		URI location = URI.create("/departments/" + savedDepartment.getId());

		return ResponseEntity
				.created(location)
				.body(savedDepartment);
	}

	@Operation(summary = "Update an existing department")
	@PutMapping
	public ResponseEntity<Department> putDepartment(@RequestBody Department department) {
		return ResponseEntity.ok(departmentService.updateDepartment(department));
	}

	@Operation(summary = "Delete a department by ID")
	@DeleteMapping("/{id}")
	public void deleteDepartment(@PathVariable long id) {
		departmentService.deleteDepartmentById(id);
	}

}
