package com.takeo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentDto {
	private Long cid;
	
	@NotBlank
	private String content;
}
