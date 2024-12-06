package com.CategoryByProducts.CategoryByProduct.controller;

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

import com.CategoryByProducts.CategoryByProduct.dto.ProductDto;
import com.CategoryByProducts.CategoryByProduct.responseApi.ApiResponse;
import com.CategoryByProducts.CategoryByProduct.serviceInterface.ProductServInterface;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	ProductServInterface productServiceInterface;

	public ProductController(ProductServInterface productServiceInterface) {
		this.productServiceInterface = productServiceInterface;
	}

	@GetMapping
	public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productServiceInterface.getAllProducts(pageable);

	}

	@PostMapping
	public ProductDto createNewProduct(@RequestBody ProductDto dto) {
		return productServiceInterface.createNewProduct(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> UpdateById(@PathVariable long id, @RequestBody ProductDto dto) {
	    return productServiceInterface.updateProduct(id, dto);
	}


	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable long id) {
		ResponseEntity<ApiResponse<ProductDto>> response = productServiceInterface.getById(id);
		return response;
	}

	@DeleteMapping("{id}")
	public String deleteProductById(@PathVariable long id) {
		return productServiceInterface.deleteById(id);
	}

}
