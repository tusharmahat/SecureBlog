package com.takeo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {
	private Long roleId;
	@NotBlank
	private String role;
}
