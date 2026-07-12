package com.global.hr.dto.request;

public record EmployeeFilter(
		String firstName,
		String lastName,
		String email,
		Long departmentId,
		String departmentName) {
}