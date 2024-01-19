package com.takeo.exceptions;

import lombok.Data;

@Data
public class PasswordMismatchException extends RuntimeException{
	private String message;

	public PasswordMismatchException(String message) {
		super();
		this.message = message;
	}
}
