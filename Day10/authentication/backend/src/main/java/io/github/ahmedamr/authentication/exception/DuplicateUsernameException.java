package io.github.ahmedamr.authentication.exception;

public class DuplicateUsernameException extends RuntimeException {
	public DuplicateUsernameException() {
		super("{error.register.username.duplicate}");
	}

	public DuplicateUsernameException(String message) {
		super(message);
	}
}
