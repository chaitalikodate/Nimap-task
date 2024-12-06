package com.CategoryByProducts.CategoryByProduct.serviceInterface;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CategoryByProducts.CategoryByProduct.dto.CategoryDto;



@Service
public interface CategoryServInterface {
	
	 Page<CategoryDto> getAllCategories(Pageable pageable);
	 CategoryDto createNewCategory(CategoryDto categoryDto);
	 ResponseEntity<Object> getById(Long id);
	 ResponseEntity<Map<String, Object>> updateCategory(Long id, CategoryDto categoryDto);
	 String deleteById(Long id);

}
