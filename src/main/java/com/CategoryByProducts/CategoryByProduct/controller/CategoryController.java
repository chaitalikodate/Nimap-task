package com.CategoryByProducts.CategoryByProduct.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CategoryByProducts.CategoryByProduct.dto.CategoryDto;
import com.CategoryByProducts.CategoryByProduct.serviceInterface.CategoryServInterface;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	CategoryServInterface categoryServiceInterface;

	public CategoryController(CategoryServInterface categoryServiceInterface) {
		this.categoryServiceInterface = categoryServiceInterface;
	}

	@GetMapping
	public Page<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return categoryServiceInterface.getAllCategories(pageable);

	}

	@PostMapping
	public CategoryDto createNewCategory(@RequestBody CategoryDto dto) {
		return categoryServiceInterface.createNewCategory(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> UpdateById(
			@PathVariable long id, @RequestBody CategoryDto dto) {
	    ResponseEntity<Map<String, Object>> response = 
	    		categoryServiceInterface.updateCategory(id, dto);
	    return response;
	}


	@GetMapping("/{id}")
	public ResponseEntity<Object> getCategoryById(@PathVariable long id) {
		return categoryServiceInterface.getById(id);
	}

	@DeleteMapping("{id}")
	public String deleteCategoryById(@PathVariable long id) {
		return categoryServiceInterface.deleteById(id);
	}
}
