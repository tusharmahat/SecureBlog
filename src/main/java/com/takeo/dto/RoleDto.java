package com.takeo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RoleDto {
	private Long roleId;
	@NotBlank
	private String role;
}
