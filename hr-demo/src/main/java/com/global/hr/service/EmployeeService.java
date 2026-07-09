package com.global.hr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.global.hr.entity.Employee;
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

	public Employee addEmployee(Employee employee) {
		log.info("adding employee: {}", employee);
		return employeeRepository.save(employee);
	}
}
