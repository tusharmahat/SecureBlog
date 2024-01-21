package com.takeo.service;

import java.util.List;

import com.takeo.dto.CategoryDto;
import com.takeo.entity.Category;

public interface CategoryService {
	
	String create (CategoryDto category);
	
	List<Category> readAll(Long categoryId);
	
	Category readCategory(Long categoryId, Long pid);
	
	Category update (CategoryDto category, Long categoryId);
	
	boolean delete(Long categoryId);
	
	
	
	
	
	

}
