package com.takeo.exceptions;

import lombok.Data;

@Data
public class InvalidPasswordException extends RuntimeException {
	private String message;

	public InvalidPasswordException(String message) {
		super();
		this.message = message;
	}

}
