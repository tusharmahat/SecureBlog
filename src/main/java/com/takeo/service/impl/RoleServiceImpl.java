package com.takeo.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.dto.RoleDto;
import com.takeo.entity.Role;
import com.takeo.exceptions.DuplicateItemException;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.RoleRepo;
import com.takeo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleDaoImpl;

	@PostConstruct
	public void init() {
		initializeDefaultRoles();
	}

	private void initializeDefaultRoles() {
		createRoleIfNotExists("Admin");
		createRoleIfNotExists("User");
	}

	private void createRoleIfNotExists(String roleName) {
		Optional<Role> existingRole = roleDaoImpl.findByRole(roleName);
		if (existingRole.isEmpty()) {
			Role newRole = new Role();
			newRole.setRole(roleName);
			roleDaoImpl.save(newRole);
		}
	}

	@Override
	public String create(RoleDto roleDto) {
		String message = "Failed to save role";
		roleDaoImpl.findByRole(roleDto.getRole())
				.orElseThrow(() -> new DuplicateItemException("The role " + roleDto.getRole() + " already exists"));
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);

		Role saveRole = roleDaoImpl.save(role);
		if (saveRole != null) {
			message = "Roled saved successfully";
		}
		return message;
	}

	@Override
	public RoleDto getRole(Long rId) {
		Role existingRole = roleDaoImpl.findById(rId)
				.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + rId + " not found"));
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(existingRole, roleDto);
		return roleDto;
	}

	@Override
	public String updateRole(RoleDto roleDto, Long rId) {
		String message = "Failed to update role";
		Role existingRole = roleDaoImpl.findById(rId)
				.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + rId + " not found"));
		roleDto.setRoleId(existingRole.getRoleId());
		BeanUtils.copyProperties(roleDto, existingRole);

		Role saveRole = roleDaoImpl.save(existingRole);
		if (saveRole != null) {
			message = "Roled updated successfully";
		}
		return message;
	}

	@Override
	public String deleteRole(Long rId) {
		String message = "Deleted Successfully";
		Role existingRole = roleDaoImpl.findById(rId)
				.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + rId + " not found"));
		roleDaoImpl.delete(existingRole);
		return message;
	}

}
