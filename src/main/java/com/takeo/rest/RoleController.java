package com.takeo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.RoleDto;
import com.takeo.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("")
	public ResponseEntity<Map<String, String>> createRole(@Valid @RequestBody RoleDto roleDto) {
		String message = "Message";
		String createRole = roleService.create(roleDto);
		Map<String, String> response = new HashMap<>();
		response.put(message, createRole);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{rId}")
	public ResponseEntity<Map<String, RoleDto>> getRole(@PathVariable("rId") Long rId) {
		String message = "Role";
		RoleDto role = roleService.getRole(rId);
		Map<String, RoleDto> response = new HashMap<>();
		response.put(message, role);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("")
	public ResponseEntity<Map<String, String>> updateRole(@Valid @RequestBody RoleDto roleDto) {
		String message = "Message";
		String updateRole = roleService.updateRole(roleDto,roleDto.getRoleId());
		Map<String, String> response = new HashMap<>();
		response.put(message, updateRole);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<Map<String, String>> deleteRole(@PathVariable("rId") Long rId) {
		String message = "Message";
		String deleteRole = roleService.deleteRole(rId);
		Map<String, String> response = new HashMap<>();
		response.put(message, deleteRole);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
