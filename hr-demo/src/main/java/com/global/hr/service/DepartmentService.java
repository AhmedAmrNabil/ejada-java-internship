package com.global.hr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.hr.dto.request.CreateDepartmentRequest;
import com.global.hr.entity.Department;
import com.global.hr.error.DuplicateResourceException;
import com.global.hr.error.ResourceNotFoundException;
import com.global.hr.repository.DepartmentRepository;

@Service
public class DepartmentService {
	private Logger log = LoggerFactory.getLogger(DepartmentService.class);

	private DepartmentRepository departmentRepository;

	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public void deleteDepartmentById(long id) {
		log.info("Deleting department with id: {}", id);
		departmentRepository.deleteById(id);
	};

	public Department updateDepartment(Department department) {
		log.info("Updating department: {}", department);
		return departmentRepository.save(department);
	}

	public Department addDepartment(Department department) {
		log.info("Adding department: {}", department);
		try {
			return departmentRepository.save(department);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateResourceException("error.department.duplicate", department.getName());
		}
	}

	public Department addDepartment(CreateDepartmentRequest departmentRequest) {
		log.info("Adding department: {}", departmentRequest);
		Department department = new Department();
		department.setName(departmentRequest.name());
		return addDepartment(department);
	}

	public Department findById(long id) {
		log.info("Finding department with id: {}", id);
		return departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("error.department.notfound", Long.valueOf(id).toString()));
	}

	public List<Department> findByName(String name) {
		log.info("Finding department with name: {}", name);
		return departmentRepository.findByName(name);
	}

	public List<Department> findAll() {
		log.info("Finding all departments");
		return departmentRepository.findAll();
	}

	public Page<Department> findAll(Pageable pageable) {
		log.info("Finding all departments with pagination: {}", pageable);
		return departmentRepository.findAll(pageable);
	}

}
