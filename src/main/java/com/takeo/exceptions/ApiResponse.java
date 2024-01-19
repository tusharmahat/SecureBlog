package com.takeo.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
	private String timestamp;
	private HttpStatus status;
	private String message;
}
