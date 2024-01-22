package com.takeo.service;

import java.util.List;

import com.takeo.dto.CommentDto;

public interface CommentService {
	public String createComment(Long uid, Long pid, CommentDto commentDto);

	public List<CommentDto> getComments(Long pid);

	public CommentDto getComment(Long cid);
	
	public String updateComment(Long cid,CommentDto commentDto);

	public String deleteComment(Long cid);
}
