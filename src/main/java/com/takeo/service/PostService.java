package com.takeo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.PostDto;

public interface PostService {

	String create(PostDto post, Long uid);

	List<PostDto> read();
	
	List<PostDto> read(Long uid);
	
	public PostDto readPost(Long pid);
	
	String update(PostDto post, Long uid, Long pid);
	
	String delete(Long pid);
	
	String updatePostPicture(MultipartFile file, Long pid);

	
	public byte[] viewPostPicture(Long pid);
	

}
