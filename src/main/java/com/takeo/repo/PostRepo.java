package com.takeo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeo.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

}
