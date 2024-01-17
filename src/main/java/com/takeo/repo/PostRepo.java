package com.takeo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeo.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

}
