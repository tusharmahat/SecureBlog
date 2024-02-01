package com.takeo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
