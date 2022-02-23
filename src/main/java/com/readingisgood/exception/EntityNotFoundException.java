package com.readingisgood.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4050583230741015520L;

	private final String message;

	public EntityNotFoundException(String entityName, String fieldName, Object value) {
		message = String.format("%s not found with %s = %s", entityName, fieldName, value);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
