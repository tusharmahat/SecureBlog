package com.takeo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.entity.Comment;
import com.takeo.service.impl.CommentServiceImpl;

@RestController
@RequestMapping("/blog/comment")
public class CommentController {

	@Autowired
	private CommentServiceImpl commentServiceImpl;

	@PostMapping("/create/{uid}/{pid}")
	public ResponseEntity<Map<String, String>> createComment(@PathVariable("uid") Long uid,
			@PathVariable("pid") Long pid, @RequestBody Comment comment) {
		String createComment = commentServiceImpl.createComment(uid,pid,comment);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, createComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
