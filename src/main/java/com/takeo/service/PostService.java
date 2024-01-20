package com.takeo.service;

import java.util.List;

import com.takeo.dto.PostDto;
import com.takeo.entity.Post;

public interface PostService {

	String create(PostDto post, Long uid);

	List<Post> read(Long uid);
	public Post readPost(Long pid);
	Post readPost(Long uid, Long pid);
	
	Post update(Post post, Long uid, Long pid);
	
	boolean delete(Long pid, Long uid);
	

}
