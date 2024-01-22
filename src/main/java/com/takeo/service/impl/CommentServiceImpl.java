package com.takeo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.dto.CommentDto;
import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.CommentRepo;
import com.takeo.repo.PostRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentDaoImpl;
	@Autowired
	private UserRepo userDaoImpl;
	@Autowired
	private PostRepo postDaoImpl;

	@Override
	public String createComment(Long uid, Long pid, CommentDto commentDto) {
		String message = "Comment failed";
		User existingUser = userDaoImpl.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("User with the uid " + uid + " does not exist"));
		Post existingPost = postDaoImpl.findById(pid)
				.orElseThrow(() -> new ResourceNotFoundException("Post with the pid " + pid + " does not exist"));
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDto, comment);
		comment.setUser(existingUser);
		comment.setPost(existingPost);
		Comment saveComment = commentDaoImpl.save(comment);
		if (saveComment != null) {
			message = "Comment added";
		}
		return message;
	}

	@Override
	public List<CommentDto> getComments(Long pid) {
		Post existingPost = postDaoImpl.findById(pid)
				.orElseThrow(() -> new ResourceNotFoundException("Post with the pid " + pid + " does not exist"));

		List<Comment> comments = existingPost.getComments();
		if (comments.size() == 0) {
			throw new ResourceNotFoundException("Post with the pid " + pid + " has no comments");
		}
		List<CommentDto> commentsDto = new ArrayList<>();
		for (Comment c : comments) {
			CommentDto cDto = new CommentDto();
			BeanUtils.copyProperties(c, cDto);
			commentsDto.add(cDto);
		}
		return commentsDto;
	}

	@Override
	public String updateComment(Long cid, CommentDto commentDto) {
		String message = "Comment not updated";
		Comment existingComment = commentDaoImpl.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Comment with the cid " + cid + " does not exist"));

		commentDto.setCid(cid);
		BeanUtils.copyProperties(commentDto, existingComment);
		Comment saveComment = commentDaoImpl.save(existingComment);
		if (saveComment != null) {
			message = "Comment updated";
		}
		return message;
	}

	@Override
	public String deleteComment(Long cid) {
		String message = "Comment deleted";
		Comment existingComment = commentDaoImpl.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Comment with the cid " + cid + " does not exist"));
		commentDaoImpl.delete(existingComment);
		return message;

	}

	@Override
	public CommentDto getComment(Long cid) {
		Comment existingComment = commentDaoImpl.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Comment with the cid " + cid + " does not exist"));
		CommentDto commentDto = new CommentDto();

		BeanUtils.copyProperties(existingComment, commentDto);

		return commentDto;
	}
}
