package com.global.hr.dto.request;

public record EmployeeFilter(
		String firstName,
		String lastName,
		String email,
		String departmentId,
		String departmentName) {
}