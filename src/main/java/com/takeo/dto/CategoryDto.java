package com.takeo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryDto {

	private long categoryId;

	@NotBlank
	private String categoryName;
}
