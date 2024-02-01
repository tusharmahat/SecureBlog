package com.takeo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

	private Long uId;

	@Email(message="Email address not valid")
	@NotBlank
	private String email;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String name;
	
	@Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
	private String mobile;

	@Max(18)
	private int age;
	
	private String gender;

	private String image;
}
