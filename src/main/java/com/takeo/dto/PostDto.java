package com.takeo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PostDto {

	private Long pid;

	@NotBlank
	private String title;

	private String image;

	@NotBlank
	private String content;
}
