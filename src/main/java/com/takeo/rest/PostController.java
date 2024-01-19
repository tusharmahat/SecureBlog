package com.takeo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.PostDto;
import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.service.impl.PostServiceImpl;
import com.takeo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/blog")
public class PostController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private PostServiceImpl postServiceImpl;

//	http://localhost:8080/blog/posts
	@PostMapping("/posts")
	public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
		User existingUser = userServiceImpl.read(postDto.getUid());
		if (existingUser != null) {
			Post post = new Post();
			post.setTitle(postDto.getTitle());
			post.setPost(postDto.getPost());
			post.setCategory(postDto.getCategory());
			post.setUser(existingUser);

			postServiceImpl.create(post);
			return ResponseEntity.ok().body("Post created successfully");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found, post not created");
	}
	@GetMapping("/posts/{uid}")
	public ResponseEntity<List<Post>> getAll(@PathVariable long uid)
	{
	List<Post> posts=postServiceImpl.read();
	if(posts!=null)
	{
		return ResponseEntity.ok(posts);
	}
	else
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts avialable for this User");

	}
}
