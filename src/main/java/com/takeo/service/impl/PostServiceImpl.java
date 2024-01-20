package com.takeo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.dto.PostDto;
import com.takeo.entity.Post;
import com.takeo.entity.User;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.PostRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postDaoImpl;

	@Autowired
	private UserRepo userDaoImpl;

	@Override
	public String create(PostDto postDto, Long uid) {
		Optional<User> existingUser = userDaoImpl.findById(uid);
		String message = "Post not created";
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			Post post = new Post();
			post.setUser(user);
			BeanUtils.copyProperties(postDto, post);

			Post savePost = postDaoImpl.save(post);
			if (savePost != null) {
				message = "Post  created";
			}
			return message;
		}
		throw new ResourceNotFoundException("User with uid " + uid + " not found");
	}

	@Override
	public List<Post> read(Long uid) {
		List<Post> posts = postDaoImpl.findAll();
		return posts;
	}

	@Override
	public Post readPost(Long pid) {
		Post post = postDaoImpl.findById(pid).get();
		return post;
	}

	@Override
	public Post readPost(Long uid, Long pid) {
		// TODO Auto-generated method stub
		Post p = readPost(pid);
		if (p != null && p.getUser().getUId() == uid) {
			return p;
		}
		return null;
	}

	@Override
	public Post update(Post post, Long uid, Long pid) {
		// TODO Auto-generated method stub

		Post p = readPost(pid);
		if (p != null && p.getUser().getUId() == uid) {
			return postDaoImpl.save(post);
		}
		return null;
	}

	@Override
	public boolean delete(Long pid, Long uid) {
		// TODO Auto-generated method stub
		Post p = readPost(pid);
		if (p != null && p.getUser().getUId() == uid) {
			postDaoImpl.deleteById(pid);
			return true;
		}
		return false;
	}
}
