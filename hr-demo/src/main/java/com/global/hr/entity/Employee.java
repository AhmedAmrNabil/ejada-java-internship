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
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private long id;

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 20)
	private String phoneNumber;

	@Column(nullable = false)
	private LocalDate hireDate;

	@Column(precision = 8, scale = 2)
	private BigDecimal salary;

	@ManyToOne(optional = false)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_department"))
	@JsonIgnore
	private Department department;

}
