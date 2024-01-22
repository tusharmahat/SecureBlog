package com.takeo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeo.dto.CategoryDto;
import com.takeo.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/blog")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl catServiceImpl;

//	http://localhost:8080/blog/category
	@PostMapping("/category")
	public ResponseEntity<Map<String, String>> createCat(@RequestBody CategoryDto category) {
		String message = "Message";
		String catSave = catServiceImpl.create(category);

		Map<String, String> response = new HashMap<>();

		response.put(message, catSave);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

//	http://localhost:8080/blog/category
	@GetMapping("/category")
	public ResponseEntity<?> getAll() {
		List<CategoryDto> category = catServiceImpl.readAll();
		String message = "Categories";

		Map<String, List<CategoryDto>> response = new HashMap<>();

		response.put(message, category);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
