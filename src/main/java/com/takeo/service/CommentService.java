package com.takeo.service;

import com.takeo.entity.Comment;

public interface CommentService {
	public String createComment(Long uid, Long pid, Comment comment);

	public String readComment(Long cid);

	public String updateComment(Comment comment);

	public String deleteComment(Long cid);
}
