package com.takeo.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeo.entity.Post;
import com.takeo.entity.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	List<Post> findByUser(User user);

	@Query("SELECT DISTINCT p FROM Post p JOIN p.categories c WHERE c.categoryTitle = :categoryTitle")
	Page<Post> findByCategoryTitle(@Param("categoryTitle") String categoryTitle,Pageable pageable);
}
