package com.CategoryByProducts.CategoryByProduct.serviceInterface.implimentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CategoryByProducts.CategoryByProduct.dto.CategoryDto;
import com.CategoryByProducts.CategoryByProduct.dto.ProductDto;
import com.CategoryByProducts.CategoryByProduct.entity.Category;
import com.CategoryByProducts.CategoryByProduct.entity.Product;
import com.CategoryByProducts.CategoryByProduct.repository.CategoryRepository;
import com.CategoryByProducts.CategoryByProduct.serviceInterface.CategoryServInterface;

@Service
public class CategoryServiceImpl implements CategoryServInterface {

	private final CategoryRepository repo;
	private final ModelMapper mapper;

	public CategoryServiceImpl(CategoryRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public Page<CategoryDto> getAllCategories(Pageable pageable) {
		Page<Category> categoriesPage = repo.findAll(pageable);
		List<CategoryDto> categoryDtoList = new ArrayList<>();

		for (Category category : categoriesPage) {
			CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
			List<ProductDto> productDtoList = new ArrayList<>();

			for (Product product : category.getProducts()) {
				ProductDto productDto = mapper.map(product, ProductDto.class);
				productDtoList.add(productDto);
			}

			categoryDto.setProducts(productDtoList);
			categoryDtoList.add(categoryDto);
		}

		return new PageImpl<>(categoryDtoList, pageable, categoriesPage.getTotalElements());
	}

	@Override
	public CategoryDto createNewCategory(CategoryDto categoryDto) {
		Optional<Category> existingCategory = repo.findByName(categoryDto.getName());

		if (existingCategory.isPresent()) {
			return mapper.map(existingCategory.get(), CategoryDto.class);
		}
		Category category = mapper.map(categoryDto, Category.class);
		Category saveCategory = repo.save(category);
		return mapper.map(saveCategory, CategoryDto.class);
	}

	@Override
	public ResponseEntity<Object> getById(Long id) {

		Optional<Category> categoryOptional = repo.findById(id);
		if (categoryOptional.isPresent()) {
			CategoryDto categoryDto = mapper.map(categoryOptional.get(), CategoryDto.class);
			return ResponseEntity.ok(categoryDto);
		}

		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", "Category with ID " + id + " not found.");
		return ResponseEntity.status(404).body(errorResponse);
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateCategory(Long id, CategoryDto categoryDto) {
		Category category = repo.findById(id).orElse(null);
		if (category == null) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Category with ID " + id + " not found.");
			errorResponse.put("data", null);
			return ResponseEntity.status(404).body(errorResponse);
		}

		category.setName(categoryDto.getName());
		Category updatedCategory = repo.save(category);
		CategoryDto updatedCategoryDto = mapper.map(updatedCategory, CategoryDto.class);

		Map<String, Object> successResponse = new HashMap<>();
		successResponse.put("message", "Category updated successfully.");
		successResponse.put("data", updatedCategoryDto);
		return ResponseEntity.ok(successResponse);
	}

	@Override
     public String deleteById(Long id) {
		if (repo.findById(id).isPresent()) {
			repo.deleteById(id);
			return "Deleted successfully";
		}
		return "Category not found";
	}
}
