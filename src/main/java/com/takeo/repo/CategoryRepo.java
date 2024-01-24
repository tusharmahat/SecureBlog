package com.takeo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeo.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Optional<Category> findByCategoryName(String categoryName);
}
