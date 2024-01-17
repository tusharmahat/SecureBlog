package com.takeo.dto;

import java.util.ArrayList;
import java.util.List;

import com.takeo.entity.Post;

import lombok.Data;

@Data
public class UserDto {

	private Long uId;

	private String uName;
	
	private List<Post> posts = new ArrayList<>();
}
