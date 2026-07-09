package com.global.hr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.global.hr.dto.request.CreateEmployeeRequest;
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
			throw new DuplicateResourceException("error.employee.duplicate", employee.getEmail());
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
		employee.setJobId(employeeRequest.jobId());
		employee.setSalary(employeeRequest.salary());
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

}
