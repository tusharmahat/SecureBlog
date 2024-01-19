package com.takeo.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
	private Date timestamp;
	private HttpStatus status;
	private String message;
}
