package com.takeo.exceptions;

public class InvalidFileExtensionException extends RuntimeException {
	private String message;

	public InvalidFileExtensionException(String message) {
		super();
		this.message = message;
	}
}
