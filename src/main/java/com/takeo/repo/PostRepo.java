package com.takeo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeo.entity.Post;
import com.takeo.entity.User;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	List<Post> findByUser(User user);
}
