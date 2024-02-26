package com.takeo.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.takeo.dto.RoleDto;
import com.takeo.entity.Role;
import com.takeo.entity.User;
import com.takeo.exceptions.DuplicateItemException;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.RoleRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.RoleService;
import com.takeo.utils.EmailService;
import com.takeo.utils.OtpGenerator;

import jakarta.annotation.PostConstruct;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleDaoImpl;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		initializeDefaultRoles();
		initializeAdminUser();
	}

	private void initializeDefaultRoles() {
		createRoleIfNotExists("ADMIN");
		createRoleIfNotExists("USER");
	}

	private void initializeAdminUser() {
		if (!userRepo.findByEmail("email@gmail.com").isPresent()) {

			try {
				createNewAdminUser("ADMIN", "email@gmail.com");
			} catch (Exception e) {
				// Handle email sending failure (log or provide a user-friendly message)
				System.out.println("Failed to send OTP. Please try again.");
			}
		}
	}

	private User createNewAdminUser(String name, String email) throws Exception {
		User adminUser = new User();
		adminUser.setName(name);
		adminUser.setEmail(email);
		adminUser.setUsername(email);

		adminUser.setPassword(passwordEncoder.encode("12345"));
		Role adminRole = roleDaoImpl.findByRole("ADMIN").orElseThrow(
				() -> new ResourceNotFoundException("Default roles initializer not working, roles not found"));

		adminRole.getRolesUsers().add(adminUser);
		adminUser.getRoles().add(adminRole);
		// Save the role
		roleDaoImpl.save(adminRole);

		return adminUser;
	}

	private void createRoleIfNotExists(String roleName) {
		Optional<Role> existingRole = roleDaoImpl.findByRole(roleName);
		if (existingRole.isEmpty()) {
			Role newRole = new Role();
			newRole.setRole(roleName);
			roleDaoImpl.save(newRole);
		}
	}

	private void sendOtp(User user) throws Exception {
		// Create and send OTP logic here
		String otp = OtpGenerator.generate();
		emailService.sendMail(user.getEmail(), "OTP", "Your OTP is " + otp);
		user.setOtp(otp);
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
