package com.takeo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {

	@Email(message = "Email address invalid")
	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
