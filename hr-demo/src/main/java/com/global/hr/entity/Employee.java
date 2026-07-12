package com.global.hr.entity;

import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Employee", description = "Represents an employee in the organization")
@Table(name = "EMPLOYEES")
@Entity
public class Employee {
	@Id
	@Column(name = "EMPLOYEE_ID")
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate hireDate;
	private String phoneNumber;
	private String jobId;
	private BigDecimal salary;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

}
