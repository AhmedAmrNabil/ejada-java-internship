package com.global.hr.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
		@Schema(example = "Validation failed") String message,
		@Schema(example = "400") int status,
		@Schema(description = "Validation errors") List<FieldError> errors) {
}