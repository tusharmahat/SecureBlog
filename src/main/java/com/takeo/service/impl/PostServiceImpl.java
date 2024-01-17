package com.takeo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.entity.Post;
import com.takeo.repo.PostRepo;
import com.takeo.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postDaoImpl;

	@Override
	public Post create(Post post) {
		return postDaoImpl.save(post);
	}

	@Override
	public List<Post> read(Long uid) {
		List<Post> posts = postDaoImpl.findAll();
		return posts;
	}

}
