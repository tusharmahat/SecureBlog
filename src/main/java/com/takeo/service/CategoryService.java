package com.takeo.service;

import java.util.List;

import com.takeo.dto.CategoryDto;
import com.takeo.entity.Category;

public interface CategoryService {
	
	String create (CategoryDto category);
	
	List<CategoryDto> readAll();
	
	CategoryDto readCategory(Long categoryId);
	
	String update (CategoryDto category, Long categoryId);
	
	String delete(Long categoryId);
	
	
	
	
	
	

}
