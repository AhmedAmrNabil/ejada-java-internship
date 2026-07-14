package io.github.ahmedamr.authentication.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
		String message,
		int status,
		List<FieldError> errors) {
}