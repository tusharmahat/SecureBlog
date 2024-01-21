package com.takeo.service;

import java.util.List;

import com.takeo.dto.PostDto;

import org.springframework.web.multipart.MultipartFile;

import com.takeo.entity.Post;

public interface PostService {

	String create(PostDto post, Long uid);

	List<Post> read(Long uid);
	public Post readPost(Long pid);
	Post readPost(Long uid, Long pid);
	
	Post update(PostDto post, Long uid, Long pid);
	
	boolean delete(Long pid, Long uid);
	
	public String updatePostPicture(MultipartFile file, Long pid);

	
	public byte[] viewPostPicture(Long pid);
	

}
