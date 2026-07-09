package com.global.hr.error;

import java.util.Arrays;

public class DuplicateResourceException extends RuntimeException {
	private final String messageCode;
	private final Object[] args;

	public DuplicateResourceException(String messageCode, Object... args) {
		this.messageCode = messageCode;
		this.args = args;
		super(String.format("Duplicate resource [%s]: %s", messageCode, Arrays.toString(args)));
	}

	public String getMessageCode() {
		return messageCode;
	}

	public Object[] getArgs() {
		return args;
	}
}
