package com.takeo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public String createComment(Long uid, Long pid, Comment comment) {
		String message = "Comment failed";
		Optional<User> existingUser = userDaoImpl.findById(uid);
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if (existingUser.isPresent() && existingPost.isPresent()) {
			comment.setUser(existingUser.get());
			comment.setPost(existingPost.get());
			Comment saveComment = commentDaoImpl.save(comment);
			if (saveComment != null) {
				message = "Comment added";
			}
			return message;
		}
		if (!existingUser.isPresent()) {
			throw new ResourceNotFoundException("User with the uid " + uid + " does not exist");
		}
		throw new ResourceNotFoundException("Post with the pid " + pid + " does not exist");
	}

	@Override
	public List<CommentDto> getComments(Long pid) {
		Optional<Post> existingPost = postDaoImpl.findById(pid);
		if (existingPost.isPresent()) {
			List<Comment> comments = existingPost.get().getComments();
			if(comments.size()==0) {
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
		throw new ResourceNotFoundException("Post with the pid " + pid + " does not exist");
	}

	@Override
	public String updateComment(Long cid, CommentDto commentDto) {
		String message = "Comment not updated";
		Optional<Comment> existingComment = commentDaoImpl.findById(cid);
		if (existingComment.isPresent()) {
			Comment comment = existingComment.get();
			commentDto.setCid(cid);
			BeanUtils.copyProperties(commentDto, comment);
			Comment saveComment = commentDaoImpl.save(comment);
			if (saveComment != null) {
				message = "Comment updated";
			}
			return message;
		}
		throw new ResourceNotFoundException("Comment with the cid " + cid + " does not exist");
	}

	@Override
	public String deleteComment(Long cid) {
		String message = "Comment deleted";
		Optional<Comment> existingComment = commentDaoImpl.findById(cid);
		if (existingComment.isPresent()) {
			Comment comm = existingComment.get();
			commentDaoImpl.delete(comm);
			return message;
		}
		throw new ResourceNotFoundException("Comment with the cid " + cid + " does not exist");
	}

	@Override
	public CommentDto getComment(Long cid) {
		Optional<Comment> existingComment = commentDaoImpl.findById(cid);
		if (existingComment.isPresent()) {
			Comment comment = existingComment.get();
			CommentDto commentDto = new CommentDto();

			BeanUtils.copyProperties(comment, commentDto);

			return commentDto;
		}
		throw new ResourceNotFoundException("Comment with the cid " + cid + " does not exist");
	}

}
