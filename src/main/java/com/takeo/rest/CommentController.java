package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.CommentDto;
import com.takeo.entity.Comment;
import com.takeo.repo.CommentRepo;
import com.takeo.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private CommentService commentService;

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PostMapping("/post/{pid}/user/{uid}")
	public ResponseEntity<Map<String, String>> createComment(@PathVariable("uid") Long uid,
			@PathVariable("pid") Long pid, @Valid @RequestBody CommentDto commentDto) {
		String createComment = commentService.createComment(uid, pid, commentDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, createComment);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/post/{pid}")
	public ResponseEntity<Map<String, List<CommentDto>>> getComments(@PathVariable("pid") Long pid) {
		List<CommentDto> getComments = commentService.getComments(pid);
		String message = "Comments of pid=" + pid;
		Map<String, List<CommentDto>> response = new HashMap<>();
		response.put(message, getComments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{cid}")
	public ResponseEntity<Map<String, CommentDto>> getComment(@PathVariable("cid") Long cid) {
		CommentDto getComment = commentService.getComment(cid);
		String message = "Comment";
		Map<String, CommentDto> response = new HashMap<>();
		response.put(message, getComment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PutMapping("/{cid}")
	public ResponseEntity<Map<String, String>> updateComment(@PathVariable("cid") Long cid,
			@RequestBody CommentDto commentDto) {
		String updateComment = commentService.updateComment(cid, commentDto);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, updateComment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@DeleteMapping("/{cid}")
	public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("cid") Long cid) {
		// Get the authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        // Check if the authenticated user is the owner of the comment
        Optional<Comment> comment = commentRepo.findById(cid);
        if (comment.isEmpty()) {
            // Handle the case where the comment does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String commentOwner = comment.get().getUser().getUsername();

        if (!authenticatedUsername.equals(commentOwner)) {
            // If the authenticated user is not the owner, return an unauthorized response
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

		
		String deleteComment = commentService.deleteComment(cid);
		String message = "Message";
		Map<String, String> response = new HashMap<>();
		response.put(message, deleteComment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
