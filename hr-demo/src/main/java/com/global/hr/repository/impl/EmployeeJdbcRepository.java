package com.global.hr.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.hr.model.Employee;
import com.global.hr.repository.EmployeeRepository;
import com.global.hr.repository.mapper.EmployeeMapper;

@Repository("employeeJdbcRepository")
public class EmployeeJdbcRepository implements EmployeeRepository {

	private JdbcTemplate jdbcTemplate;

	public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Employee findById(long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM employees WHERE employee_id = ?",
				new EmployeeMapper(),
				id);
	}

	@Override
	public List<Employee> findAll() {
		return jdbcTemplate.query(
				"SELECT * FROM employees",
				new EmployeeMapper());
	}

	@Override
	public int count() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employees", Integer.class);
	}

	@Override
	public void insert(Employee employee) {
		jdbcTemplate.update(
				"INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary) VALUES (?, ?, ?, ?, ?, ?, ?)",
				employee.getFirstName(),
				employee.getLastName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getHireDate(),
				employee.getJobId(),
				employee.getSalary());
	}

	@Override
	public void update(Employee employee) {
		jdbcTemplate.update(
				"UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ? WHERE employee_id = ?",
				employee.getFirstName(),
				employee.getLastName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getHireDate(),
				employee.getJobId(),
				employee.getSalary(),
				employee.getId());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM employees WHERE employee_id = ?", id);
	}
}
