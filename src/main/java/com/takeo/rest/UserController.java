package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.takeo.entity.User;
import com.takeo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/blog")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

//	http://localhost:8080/blog/users
	@PostMapping("/users")
	public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
		String userRegistration = userServiceImpl.register(user);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, userRegistration);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/users
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> users = userServiceImpl.read();

		return ResponseEntity.ok().body(users);
	}

//	http://localhost:8080/blog/users/1
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long uid) {

		boolean result = userServiceImpl.delete(uid);
		String message = "Not deleted";
		if (result) {
			message = "Deleted";
			return ResponseEntity.ok().body(message);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

//	http://localhost:8080/blog/users/1
	@PutMapping("/users")
	public ResponseEntity<Map<String, Object>> putUser(@RequestBody UserDto user) {
		UserDto userDto = userServiceImpl.update(user);
		String message = "Message";
		Map<String, Object> response = new HashMap<>();
		response.put(message, userDto);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

//	http://localhost:8080/blog/user/verify/{otp}
	@PostMapping("/user/verify/{otp}")
	public ResponseEntity<Map<String, String>> verifyOtp(@PathVariable("otp") String otp) {
		String verifyOtp = userServiceImpl.verifyOtp(otp);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, verifyOtp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/login
	@GetMapping("/user/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
		String login = userServiceImpl.userLogin(loginDto.getEmail(), loginDto.getPassword());
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/forgotpassword/{email}
	@PostMapping("user/forgotpassword/{email}")
	public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable String email) {
		String resetPassword = userServiceImpl.forgotPassword(email);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, resetPassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/changepassword
	@PostMapping("user/changepassword")
	public ResponseEntity<Map<String, String>> changePassword(@RequestBody ResetPasswordDto resetPassDto) {
		String changePassword = userServiceImpl.changePassword(resetPassDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, changePassword);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/updateprofilepic
	@PostMapping("user/updateprofilepic")
	public ResponseEntity<Map<String, String>> updateProfilePic(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email) {
		String updatePicture = userServiceImpl.updateProfilePicture(file, email);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updatePicture);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	http://localhost:8080/blog/user/profilepic/{email}
	@GetMapping("user/profilepic/{email}")
	public ResponseEntity<byte[]> getProfilePic(@PathVariable("email") String email) {
		byte[] profilePic = userServiceImpl.viewProfilePicture(email);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(profilePic);
	}

}
