package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.PostDto;
import com.takeo.service.impl.PostServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostServiceImpl postServiceImpl;

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PostMapping("/user/{uid}/cat/{categoryName}")
	public ResponseEntity<Map<String, String>> createPost(@PathVariable("uid") Long uid,
			@PathVariable("categoryName") String categoryName, @Valid @RequestBody PostDto postDto) {

		String message = "Message";
		String postSave = postServiceImpl.create(postDto, uid, categoryName);

		Map<String, String> response = new HashMap<>();

		response.put(message, postSave);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/user/{uid}")
	public ResponseEntity<?> getAllFromUser(@PathVariable("uid") long uid) {
		List<PostDto> posts = postServiceImpl.read(uid);
		String message = "Posts:";
		Map<String, List<PostDto>> response = new HashMap<>();
		response.put(message, posts);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		List<PostDto> posts = postServiceImpl.read();
		String message = "Posts:";
		Map<String, List<PostDto>> response = new HashMap<>();
		response.put(message, posts);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/bycategory")
	public ResponseEntity<?> getByCat(@RequestParam(name = "cat") String cat, Pageable pageable) {
		Page<PostDto> category = postServiceImpl.readCatPost(cat, pageable);

		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, PostDto>> get(@PathVariable("id") Long pid) {
		PostDto post = postServiceImpl.readPost(pid);
		String message = "Posts:";
		Map<String, PostDto> response = new HashMap<>();
		response.put(message, post);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PutMapping("/{pid}/user/{uid}")
	public ResponseEntity<Map<String, String>> updatepost(@PathVariable("pid") long pid, @PathVariable("uid") long uid,
			@Valid @RequestBody PostDto post) {
		String existingPost = postServiceImpl.update(post, uid, pid);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, existingPost);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deletePost(@PathVariable("id") long pid) {
		String deletePost = postServiceImpl.delete(pid);
		String message = "Message";

		Map<String, String> response = new HashMap<>();

		response.put(message, deletePost);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PutMapping("/pic")
	public ResponseEntity<Map<String, String>> updatePostPic(@RequestParam("file") MultipartFile file,
			@RequestParam("pid") Long pid) {
		String updatePicture = postServiceImpl.updatePostPicture(file, pid);
		String message = "Message";

		Map<String, String> response = new HashMap<>();

		response.put(message, updatePicture);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/pic/{pid}")
	public ResponseEntity<byte[]> getPostPic(@PathVariable("pid") Long pid) {
		byte[] postPic = postServiceImpl.viewPostPicture(pid);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(postPic);
	}

}
