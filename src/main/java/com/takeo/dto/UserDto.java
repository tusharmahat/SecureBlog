package com.takeo.dto;

import lombok.Data;

@Data
public class UserDto {

	private Long uId;

	private String email;
	
	private String mobile;
	
	private int age;

	private String gender;

	private String image;

	private long roleId;
}
