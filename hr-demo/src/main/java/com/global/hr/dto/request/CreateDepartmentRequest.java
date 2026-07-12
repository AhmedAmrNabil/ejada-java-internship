package com.global.hr.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDepartmentRequest(
		@NotBlank(message = "{error.validation.department.name.blank}") String name) {
}
