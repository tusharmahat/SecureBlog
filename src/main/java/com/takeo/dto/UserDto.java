package com.takeo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {

	private Long uId;

	@Email(message="Email address not valid")
	@NotBlank
	private String email;
	
	@NotBlank
	private String name;
	
	@Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
	private String mobile;

	@Max(18)
	private int age;
	
	private String gender;

	private String image;

	private long roleId;
}
