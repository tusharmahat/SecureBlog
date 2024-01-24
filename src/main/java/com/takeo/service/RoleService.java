package com.takeo.service;

import com.takeo.dto.RoleDto;

public interface RoleService {
	String create(RoleDto roleDto);

	RoleDto getRole(Long rId);

	String updateRole(RoleDto roleDto,Long rId);

	String deleteRole(Long rId);
}
