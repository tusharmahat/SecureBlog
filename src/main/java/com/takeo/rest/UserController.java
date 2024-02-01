package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.LoginDto;
import com.takeo.dto.ResetPasswordDto;
import com.takeo.dto.UserDto;
import com.takeo.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PreAuthorize("permitAll()")
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserDto userDto) {
		String userRegistration = userServiceImpl.register(userDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, userRegistration);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<UserDto>> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Authorities: " + authentication.getAuthorities());

		List<UserDto> users = userServiceImpl.read();
		return ResponseEntity.ok().body(users);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and #username == authentication.principal.username")
	@PutMapping("/{username}")
	public ResponseEntity<Map<String, String>> putUser(@PathVariable("username") String username,@Valid @RequestBody UserDto userDto) {
		String updateUser = userServiceImpl.update(userDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updateUser);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PreAuthorize("permitAll()")
	@PostMapping("/verify/{otp}")
	public ResponseEntity<Map<String, String>> verifyOtp(@PathVariable("otp") String otp) {
		String verifyOtp = userServiceImpl.verifyOtp(otp);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, verifyOtp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/login")
	public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDto loginDto) {
		String login = userServiceImpl.userLogin(loginDto.getUsername(), loginDto.getPassword());
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@PostMapping("/forgotpassword/{email}")
	public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable String email) {
		String resetPassword = userServiceImpl.forgotPassword(email);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, resetPassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and #username == authentication.principal.username")
	@PutMapping("/password/{username}")
	public ResponseEntity<Map<String, String>> changePassword(@PathVariable("username") String username,@Valid @RequestBody ResetPasswordDto resetPassDto) {
		String changePassword = userServiceImpl.changePassword(resetPassDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, changePassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and #username == authentication.principal.username")
	@PutMapping("/pic/{username}")
	public ResponseEntity<Map<String, String>> updateProfilePic(@RequestParam("file") MultipartFile file,
			@PathVariable("username") String username) {
		String updatePicture = userServiceImpl.updateProfilePicture(file, username);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updatePicture);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER') and #username == authentication.principal.username")
	@GetMapping("/pic/{username}")
	public ResponseEntity<byte[]> getProfilePic(@PathVariable("username") String username) {
		byte[] profilePic = userServiceImpl.viewProfilePicture(username);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(profilePic);
	}

}
