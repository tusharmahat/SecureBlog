package com.takeo.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takeo.entity.Post;

import lombok.Data;

@Data
public class UserDto {

	private Long uId;

	private String uName;

	private String email;
	
	private String mobile;
	
	private int age;

	private String gender;

	private String image;

	private long roleId;

	private List<Post> posts = new ArrayList<>();
}
