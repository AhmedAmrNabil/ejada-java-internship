package com.global.hr.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
		String message,
		int status,
		List<FieldError> errors // null when it's not a validation error
) {
}