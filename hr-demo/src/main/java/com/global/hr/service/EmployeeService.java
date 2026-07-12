package com.global.hr.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException.ConstraintKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.hr.dto.request.CreateEmployeeRequest;
import com.global.hr.entity.Department;
import com.global.hr.entity.Employee;
import com.global.hr.error.DuplicateResourceException;
import com.global.hr.error.ResourceNotFoundException;
import com.global.hr.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private Logger log = LoggerFactory.getLogger(EmployeeService.class);

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public void deleteEmployeeById(long id) {
		log.info("Deleting employee with id: {}", id);
		employeeRepository.deleteById(id);
	};

	public Employee updateEmployee(Employee employee) {
		log.info("Updating employee: {}", employee);
		return employeeRepository.save(employee);
	}

	public Employee addEmployee(Employee employee) {
		log.info("Adding employee: {}", employee);
		try {
			return employeeRepository.save(employee);
		} catch (DataIntegrityViolationException e) {
			// TODO: better handle different foreign key constraints
			if (e.getCause() instanceof ConstraintViolationException cve) {
				ConstraintKind constraintKind = cve.getKind();
				if (constraintKind == ConstraintKind.UNIQUE) {
					throw new DuplicateResourceException("error.employee.duplicate", employee.getEmail());
				}
				if (constraintKind == ConstraintKind.FOREIGN_KEY) {
					throw new ResourceNotFoundException("error.department.notfound",
							Long.valueOf(employee.getDepartment().getId()).toString());
				}
			}
			throw e;
		}
	}

	public Employee addEmployee(CreateEmployeeRequest employeeRequest) {
		log.info("Adding employee: {}", employeeRequest);
		Employee employee = new Employee();
		employee.setFirstName(employeeRequest.firstName());
		employee.setLastName(employeeRequest.lastName());
		employee.setEmail(employeeRequest.email());
		employee.setPhoneNumber(employeeRequest.phoneNumber());
		employee.setHireDate(employeeRequest.hireDate());
		employee.setSalary(employeeRequest.salary());
		Department department = new Department();
		department.setId(employeeRequest.departmentId());
		employee.setDepartment(department);
		return addEmployee(employee);
	}

	public Employee findById(long id) {
		log.info("Finding employee with id: {}", id);
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("error.employee.notfound", Long.valueOf(id).toString()));
	}

	public List<Employee> findByFirstName(String firstName) {
		log.info("Finding employees with first name: {}", firstName);
		return employeeRepository.findByFirstName(firstName);
	}

	public List<Employee> findAll() {
		log.info("Finding all employees");
		return employeeRepository.findAll();
	}

	public Page<Employee> findAll(Pageable pageable) {
		log.info("Finding all employees with pagination: {}", pageable);
		return employeeRepository.findAll(pageable);
	}

	public Page<Employee> findByDepartmentId(long departmentId, Pageable pageable) {
		log.info("Finding employees with department id: {} and pagination: {}", departmentId, pageable);
		return employeeRepository.findByDepartmentId(departmentId, pageable);
	}

}
