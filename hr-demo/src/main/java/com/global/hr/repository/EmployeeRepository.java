package com.global.hr.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.hr.model.Employee;

@Repository
public class EmployeeRepository {

	private JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Employee findById(long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM employees WHERE employee_id = ?",
				BeanPropertyRowMapper.newInstance(Employee.class),
				id);
	}

	public List<Employee> findAll() {
		return jdbcTemplate.query(
				"SELECT * FROM employees",
				BeanPropertyRowMapper.newInstance(Employee.class));
	}
}
