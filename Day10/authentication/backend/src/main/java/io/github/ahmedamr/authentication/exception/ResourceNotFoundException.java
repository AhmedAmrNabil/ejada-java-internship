package io.github.ahmedamr.authentication.exception;

import java.util.Arrays;

public class ResourceNotFoundException extends RuntimeException {

	private final String messageCode;
	private final Object[] args;

	public ResourceNotFoundException(String message) {
		super(message);
		this.messageCode = message;
		this.args = null;
	}

	public ResourceNotFoundException() {
		super("{error.resource-not-found}");
		this.messageCode = "{error.resource-not-found}";
		this.args = null;
	}

	public ResourceNotFoundException(String messageCode, Object... args) {
		this.messageCode = messageCode;
		this.args = args;
		super(String.format("Resource not found [%s]: %s", messageCode, Arrays.toString(args)));
	}

	public String getMessageCode() {
		return messageCode;
	}

	public Object[] getArgs() {
		return args;
	}
}
