package com.global.hr.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.global.hr.model.Employee;

public class EmployeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getLong("employee_id"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setLastName(rs.getString("last_name"));
		employee.setEmail(rs.getString("email"));
		employee.setPhoneNumber(rs.getString("phone_number"));
		employee.setHireDate(rs.getDate("hire_date").toLocalDate());
		employee.setJobId(rs.getString("job_id"));
		employee.setSalary(rs.getBigDecimal("salary"));
		return employee;
	}
}
