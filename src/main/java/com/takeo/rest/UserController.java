package com.takeo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		userServiceImpl.create(user);
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
	@GetMapping("/users/posts/{id}")
	public ResponseEntity<List<Post>> getUserPosts(@PathVariable("id") Long userId) {
		User user = userServiceImpl.read(userId);

		if (user != null) {
			System.out.println("User found");
			List<Post> posts = user.getPosts();
			return ResponseEntity.ok(posts);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
