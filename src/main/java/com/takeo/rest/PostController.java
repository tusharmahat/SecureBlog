package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.PostDto;
import com.takeo.entity.Post;
import com.takeo.service.impl.PostServiceImpl;

@RestController
@RequestMapping("/blog")
public class PostController {

	@Autowired
	private PostServiceImpl postServiceImpl;

//	http://localhost:8080/blog/posts
	@PostMapping("/posts")
	public ResponseEntity<Map<String, String>> createPost(@RequestBody PostDto postDto) {
		String message = "Message";
		String postSave = postServiceImpl.create(postDto, postDto.getUid());

		Map<String, String> response = new HashMap<>();

		response.put(message, postSave);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// get all posts of a user
	@GetMapping("/posts/users/{uid}")
	public ResponseEntity<?> getAll(@PathVariable long uid) {
		List<Post> posts = postServiceImpl.read(uid);
		if (posts != null) {
			return ResponseEntity.ok(posts);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts avialable for this User");

	}

	// Get posts from post id
	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> get(@PathVariable("id") Long pid) {
		Post post = postServiceImpl.readPost(pid);

		return ResponseEntity.ok(post);
	}

	@PutMapping("/posts/{uid}/update/{pid}")
	public ResponseEntity<String> updatepost(@PathVariable("pid") long pid, @PathVariable("uid") long uid,@RequestBody PostDto post) {
		Post existingPost = postServiceImpl.update(post, uid, pid);
		String message = "Post not updated";

		if (existingPost != null) {
			message = "Post details updated";
		
			return ResponseEntity.ok().body(message);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long pid) {
		boolean result = postServiceImpl.delete(pid, pid);
		String message = "Not deleted";

		if (result) {
			message = "Deleted";
			return ResponseEntity.ok().body(message);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
}
