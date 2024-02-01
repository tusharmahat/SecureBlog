package com.takeo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {
	private Long cid;
	
	@NotBlank
	private String content;
}
