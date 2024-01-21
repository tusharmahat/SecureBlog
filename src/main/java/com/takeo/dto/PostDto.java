package com.takeo.dto;

import lombok.Data;

@Data
public class PostDto {

	private Long pid;

	private String title;
	
	private String image;

	private String content;
}
