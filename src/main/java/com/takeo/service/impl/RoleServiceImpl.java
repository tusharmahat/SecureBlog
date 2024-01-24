package com.takeo.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleDaoImpl;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailService emailService;

	@PostConstruct
	public void init() {
	    initializeDefaultRoles();
	    initializeAdminUser();
	}

	private void initializeDefaultRoles() {
	    createRoleIfNotExists("Admin");
	    createRoleIfNotExists("User");
	}

	private void initializeAdminUser() {
	    if (!userRepo.findByEmail("silencenature123@gmail.com").isPresent()) {
	        User adminUser = createNewAdminUser("admin", "silencenature123@gmail.com");

	        try {
	            // Send OTP via email
	            sendOtp(adminUser);
	        } catch (Exception e) {
	            // Handle email sending failure (log or provide a user-friendly message)
	            System.out.println("Failed to send OTP. Please try again.");
	        }
	    }
	}

	private User createNewAdminUser(String name, String email) {
	    User adminUser = new User();
	    adminUser.setName(name);
	    adminUser.setEmail(email);

	    Role adminRole = roleDaoImpl.findByRole("Admin").orElseThrow(
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
