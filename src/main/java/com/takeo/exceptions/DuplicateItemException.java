package com.takeo.exceptions;

import lombok.Data;

@Data
public class DuplicateItemException extends RuntimeException {
	private String message;

	public DuplicateItemException(String message) {
		super();
		this.message = message;
	}

}
