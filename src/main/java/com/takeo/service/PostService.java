package com.takeo.service;

import java.util.List;

import com.takeo.entity.Post;

public interface PostService {

	Post create(Post post);

	List<Post> read(Long uid);

}
