package com.global.hr.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.hr.model.Employee;
import com.global.hr.repository.EmployeeRepository;
import com.global.hr.repository.mapper.EmployeeMapper;

@Repository("employeeNamedJdbcRepository")
public class EmployeeNamedJdbcRepository implements EmployeeRepository {

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public EmployeeNamedJdbcRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	@Override
	public Employee findById(long id) {
		return namedJdbcTemplate.queryForObject(
				"SELECT * FROM employees WHERE employee_id = :id",
				new MapSqlParameterSource("id", id),
				new EmployeeMapper());
	}

	@Override
	public List<Employee> findAll() {
		return namedJdbcTemplate.query(
				"SELECT * FROM employees",
				new EmployeeMapper());
	}

	@Override
	public int count() {
		return namedJdbcTemplate.queryForObject("SELECT COUNT(*) FROM employees",
				new MapSqlParameterSource(),
				Integer.class);
	}

	@Override
	public void insert(Employee employee) {
		namedJdbcTemplate.update(
				"INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary) VALUES (:firstName, :lastName, :email, :phoneNumber, :hireDate, :jobId, :salary)",
				new BeanPropertySqlParameterSource(employee));
	}

	@Override
	public void update(Employee employee) {
		namedJdbcTemplate.update(
				"UPDATE employees SET first_name = :firstName, last_name = :lastName, email = :email, phone_number = :phoneNumber, hire_date = :hireDate, job_id = :jobId, salary = :salary WHERE employee_id = :id",
				new BeanPropertySqlParameterSource(employee));
	}

	@Override
	public void delete(long id) {
		namedJdbcTemplate.update("DELETE FROM employees WHERE employee_id = :id",
				new MapSqlParameterSource("id", id));
	}
}
