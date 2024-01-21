package com.takeo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeo.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
