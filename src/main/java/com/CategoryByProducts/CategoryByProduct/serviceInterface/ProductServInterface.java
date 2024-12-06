package com.CategoryByProducts.CategoryByProduct.serviceInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CategoryByProducts.CategoryByProduct.dto.ProductDto;
import com.CategoryByProducts.CategoryByProduct.responseApi.ApiResponse;

@Service
public interface ProductServInterface {
	Page<ProductDto> getAllProducts(Pageable pageable);
	 ProductDto createNewProduct(ProductDto productDto);
	 ResponseEntity<ApiResponse<ProductDto>> getById(Long id);
	 ResponseEntity<ApiResponse<ProductDto>> updateProduct(Long id, ProductDto Dto);
	 String deleteById(Long id);

}
