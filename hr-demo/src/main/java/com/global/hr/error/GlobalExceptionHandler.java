package com.global.hr.error;

import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.global.hr.dto.response.ApiError;
import com.global.hr.dto.response.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiError> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
		String message = messageSource.getMessage(
				ex.getMessageCode(), ex.getArgs(), ex.getMessage(), request.getLocale());

		ApiError apiError = new ApiError(message, 409, null);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		String message = messageSource.getMessage(
				"error.validation",
				null,
				"Validation failed",
				request.getLocale());
		var errors = Stream.concat(
				ex.getBindingResult().getFieldErrors().stream()
						.map(error -> new FieldError(error.getField(), error.getDefaultMessage())),
				ex.getBindingResult().getGlobalErrors().stream()
						.map(error -> new FieldError("global", error.getDefaultMessage())))
				.toList();

		ApiError apiError = new ApiError(message, 422, errors);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(apiError);
	}
}
