package io.github.ahmedamr.authentication.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.ahmedamr.authentication.dto.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
		String message = messageSource.getMessage(ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiError(message, HttpStatus.UNAUTHORIZED.value(), null));
	}

	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<ApiError> handleDuplicateUsername(DuplicateUsernameException ex) {
		String message = messageSource.getMessage(ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiError(message, HttpStatus.CONFLICT.value(), null));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
		String message = messageSource.getMessage(ex.getMessageCode(), ex.getArgs(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(message, HttpStatus.NOT_FOUND.value(), null));
	}

	@ExceptionHandler(RevokedRefreshTokenException.class)
	public ResponseEntity<ApiError> handleRevokedRefreshToken(RevokedRefreshTokenException ex) {
		String message = messageSource.getMessage(ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new ApiError(message, HttpStatus.FORBIDDEN.value(), null));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGenericException(Exception ex) {
		log.error("An unexpected error occurred", ex.getClass().getName() + ": " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiError("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
	}
}
