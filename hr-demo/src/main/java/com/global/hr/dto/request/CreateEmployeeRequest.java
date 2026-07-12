package com.global.hr.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEmployeeRequest(
		@NotBlank(message = "{error.validation.employee.firstName.blank}") String firstName,

		@NotBlank(message = "{error.validation.employee.lastName.blank}") String lastName,

		@NotBlank(message = "{error.validation.employee.email.blank}") @Email(message = "{error.validation.employee.email.invalid}") String email,

		@NotBlank(message = "{error.validation.employee.phoneNumber.blank}") String phoneNumber,

		@NotNull(message = "{error.validation.employee.hireDate.blank}") LocalDate hireDate,

		@NotNull @Positive(message = "{error.validation.employee.salary.positive}") BigDecimal salary,
		@NotNull(message = "{error.validation.employee.departmentId.blank}") Long departmentId) {
}
