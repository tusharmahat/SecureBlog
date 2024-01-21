package com.takeo.service;

import java.util.List;

import com.takeo.dto.CommentDto;
import com.takeo.entity.Comment;

public interface CommentService {
	public String createComment(Long uid, Long pid, Comment comment);

	public List<CommentDto> getComments(Long pid);

	public CommentDto getComment(Long cid);
	
	public String updateComment(Long cid,CommentDto comment);

	public String deleteComment(Long cid);
}
