package com.global.hr.error;

import java.util.Arrays;

public class ResourceNotFoundException extends RuntimeException {
	private final String messageCode;
	private final Object[] args;

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
