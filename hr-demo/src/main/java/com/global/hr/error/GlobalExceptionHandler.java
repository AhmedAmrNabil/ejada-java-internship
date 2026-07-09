package com.global.hr.error;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.global.hr.dto.response.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		String message = messageSource.getMessage(
				ex.getMessageCode(), ex.getArgs(), ex.getMessage(), request.getLocale());

		ApiError apiError = new ApiError(message, 404, null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
}
