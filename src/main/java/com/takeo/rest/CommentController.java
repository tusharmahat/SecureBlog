package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.CommentDto;
import com.takeo.service.CommentService;

@RestController
@RequestMapping("/blog/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/create/{uid}/{pid}")
	public ResponseEntity<Map<String, String>> createComment(@PathVariable("uid") Long uid,
			@PathVariable("pid") Long pid, @Valid @RequestBody CommentDto commentDto) {
		String createComment = commentService.createComment(uid, pid, commentDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, createComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/get/{pid}")
	public ResponseEntity<Map<String, List<CommentDto>>> getComments(@PathVariable("pid") Long pid) {
		List<CommentDto> getComments = commentService.getComments(pid);
		String message = "Comments of pid=" + pid;
		Map<String, List<CommentDto>> response = new HashMap<>();
		response.put(message, getComments);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getone/{cid}")
	public ResponseEntity<Map<String, CommentDto>> getComment(@PathVariable("cid") Long cid) {
		CommentDto getComment = commentService.getComment(cid);
		String message = "Comment";
		Map<String, CommentDto> response = new HashMap<>();
		response.put(message, getComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update/{cid}")
	public ResponseEntity<Map<String, String>> updateComment(@PathVariable("cid") Long cid,
			@RequestBody CommentDto commentDto) {
		String updateComment = commentService.updateComment(cid, commentDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updateComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{cid}")
	public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("cid") Long cid) {
		String deleteComment = commentService.deleteComment(cid);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, deleteComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
