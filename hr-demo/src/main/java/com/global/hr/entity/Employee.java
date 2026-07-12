package com.global.hr.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Employee", description = "Represents an employee in the organization")
@Table(name = "EMPLOYEES", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email", name = "uq_email")
})
@Entity
@JsonPropertyOrder({ "id", "firstName", "lastName", "email", "phoneNumber", "hireDate", "salary", "department" })
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private long id;

	@NotBlank
	@Column(length = 50)
	private String firstName;

	@NotBlank
	@Column(length = 50)
	private String lastName;

	@NotBlank
	@Email
	@Column(length = 100)
	private String email;

	@Column(length = 20)
	private String phoneNumber;

	@NotNull
	private LocalDate hireDate;

	@Digits(integer = 6, fraction = 2)
	private BigDecimal salary;

	@ManyToOne(optional = false)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_department"))
	@JsonIgnore
	private Department department;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
