package com.takeo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.User;
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
		}

		return message;
	}

	@Override
	public String readComment(Long cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateComment(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteComment(Long cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
