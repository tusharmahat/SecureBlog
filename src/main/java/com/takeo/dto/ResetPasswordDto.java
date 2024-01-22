package com.takeo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ResetPasswordDto {
	@NotBlank
	@Email(message = "Email address invalid")
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String confirmPassword;

}
