package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping("/blog/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

//	http://localhost:8080/blog/users
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserDto userDto) {
		String userRegistration = userServiceImpl.register(userDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, userRegistration);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/users
	@GetMapping("/get")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> users = userServiceImpl.read();

		return ResponseEntity.ok().body(users);
	}

//	http://localhost:8080/blog/users/1
	@PutMapping("/update")
	public ResponseEntity<Map<String, String>> putUser(@Valid @RequestBody UserDto userDto) {
		String updateUser = userServiceImpl.update(userDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updateUser);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

//	http://localhost:8080/blog/user/verify/{otp}
	@PostMapping("/verify/{otp}")
	public ResponseEntity<Map<String, String>> verifyOtp(@PathVariable("otp") String otp) {
		String verifyOtp = userServiceImpl.verifyOtp(otp);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, verifyOtp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/login
	@GetMapping("/login")
	public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDto loginDto) {
		String login = userServiceImpl.userLogin(loginDto.getEmail(), loginDto.getPassword());
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/forgotpassword/{email}
	@PostMapping("/forgotpassword/{email}")
	public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable String email) {
		String resetPassword = userServiceImpl.forgotPassword(email);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, resetPassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/changepassword
	@PostMapping("/changepassword")
	public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ResetPasswordDto resetPassDto) {
		String changePassword = userServiceImpl.changePassword(resetPassDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, changePassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/updateprofilepic
	@PostMapping("/updateprofilepic")
	public ResponseEntity<Map<String, String>> updateProfilePic(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email) {
		String updatePicture = userServiceImpl.updateProfilePicture(file, email);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updatePicture);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/profilepic/{email}
	@GetMapping("/profilepic/{email}")
	public ResponseEntity<byte[]> getProfilePic(@PathVariable("email") String email) {
		byte[] profilePic = userServiceImpl.viewProfilePicture(email);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(profilePic);
	}

}
