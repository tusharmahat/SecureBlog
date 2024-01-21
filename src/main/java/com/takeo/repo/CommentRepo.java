package com.takeo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.User;


@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
	List<Comment> findByUser(User user);
	List<Comment> findByPost(Post post);
}
