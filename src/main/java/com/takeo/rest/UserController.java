package com.takeo.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/blog")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

//	http://localhost:8080/blog/users
	@PostMapping("/users")
	public ResponseEntity<String> register(@RequestBody User user) {
		userServiceImpl.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created");
	}

//	http://localhost:8080/blog/users
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAll() {
		List<User> users = userServiceImpl.read();
		List<UserDto> usersDto = new ArrayList<>();
		users.forEach(user -> {
			UserDto u = new UserDto();
			BeanUtils.copyProperties(user, u);
			usersDto.add(u);

		});

		return ResponseEntity.ok().body(usersDto);
	}

//	http://localhost:8080/blog/users/1
	@GetMapping("/users/{id}")
	public User get(@PathVariable("id") Long uid) {
		User user = userServiceImpl.read(uid);
		return user;
	}

//	http://localhost:8080/blog/users/posts/1
	@GetMapping("/users/{id}/posts")
	public ResponseEntity<List<Post>> getUserPosts(@PathVariable("id") Long uid) {
		User user = userServiceImpl.read(uid);

		if (user != null) {
			List<Post> posts = user.getPosts();
			return ResponseEntity.ok(posts);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
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
	@PutMapping("/users/{id}")
	public ResponseEntity<String> putUser(@PathVariable("id") Long uid, @RequestBody UserDto user) {
		User existingUser = userServiceImpl.read(uid);
		String message = "User Datails not updated";
		if (existingUser != null) {
			message = "User Datails updated";
			user.setUId(existingUser.getUId());
			BeanUtils.copyProperties(user, existingUser, "uid");
			userServiceImpl.update(existingUser);
			return ResponseEntity.ok().body(message);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

//	http://localhost:8080/blog/user/verify/{otp}
	@PostMapping("/user/verify/{otp}")
	public ResponseEntity<String> verifyOtp(@PathVariable("otp") String otp) {
		String verifyOtp = userServiceImpl.verifyOtp(otp);

		return ResponseEntity.status(HttpStatus.CREATED).body(verifyOtp);
	}

//	http://localhost:8080/blog/user/login
	@GetMapping("/user/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		String login = userServiceImpl.userLogin(loginDto.getEmail(), loginDto.getPassword());
		return ResponseEntity.ok().body(login);
	}

//	http://localhost:8080/blog/user/forgotpassword/{email}
	@PostMapping("user/forgotpassword/{email}")
	public ResponseEntity<Map<String, Object>> forgotPassword(@PathVariable String email) {
		String resetPassword = userServiceImpl.forgotPassword(email);
		Map<String, Object> map = new HashMap<>();
		map.put("message", resetPassword);
		return ResponseEntity.ok().body(map);
	}

//	http://localhost:8080/blog/user/changepassword
	@PostMapping("user/changepassword")
	public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ResetPasswordDto resetPassDto) {
		String changePassword = userServiceImpl.changePassword(resetPassDto);
		Map<String, Object> map = new HashMap<>();
		map.put("message", changePassword);
		return ResponseEntity.ok().body(map);
	}

//	http://localhost:8080/blog/user/updateprofilepic
	@PostMapping("user/updateprofilepic")
	public ResponseEntity<Map<String, Object>> updateProfilePic(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email) {
		String message = userServiceImpl.updateProfilePicture(file, email);
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		return ResponseEntity.ok().body(map);
	}
	
//	http://localhost:8080/blog/user/profilepic/{email}
	@GetMapping("user/profilepic/{email}")
	public ResponseEntity<byte[]> getProfilePic(@PathVariable("email") String email) {
		byte[] profilePic = userServiceImpl.viewProfilePicture(email);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
				.body(profilePic);
	}

}
