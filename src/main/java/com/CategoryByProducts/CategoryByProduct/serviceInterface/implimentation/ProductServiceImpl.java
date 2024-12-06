package com.CategoryByProducts.CategoryByProduct.serviceInterface.implimentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CategoryByProducts.CategoryByProduct.dto.ProductDto;
import com.CategoryByProducts.CategoryByProduct.entity.Product;
import com.CategoryByProducts.CategoryByProduct.repository.ProductRepository;
import com.CategoryByProducts.CategoryByProduct.responseApi.ApiResponse;
import com.CategoryByProducts.CategoryByProduct.serviceInterface.ProductServInterface;

@Service
public class ProductServiceImpl implements ProductServInterface {
	private final ProductRepository repo;
	private final ModelMapper mapper;

	public ProductServiceImpl(ProductRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public Page<ProductDto> getAllProducts(Pageable pageable) {
		Page<Product> productsPage = repo.findAll(pageable);

		List<ProductDto> productDtoList = new ArrayList<>();
		for (Product product : productsPage.getContent()) {
			ProductDto productDto = mapper.map(product, ProductDto.class);

			if (product.getCategory() != null) {
				productDto.setCategoryId(product.getCategory().getId()); // Set the category ID
			}

			productDtoList.add(productDto);
		}

		return new PageImpl<>(productDtoList, pageable, productsPage.getTotalElements());
	}

	@Override
	public ProductDto createNewProduct(ProductDto productDto) {
		Optional<Product> existingProduct = repo.findByNameAndQuantityAndPrice(productDto.getName(),
				productDto.getQuantity(), productDto.getPrice());

		if (existingProduct.isPresent()) {
			return mapper.map(existingProduct.get(), ProductDto.class);
		}
		Product product = mapper.map(productDto, Product.class);
		Product saveProduct = repo.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ResponseEntity<ApiResponse<ProductDto>> getById(Long id) {
	    Optional<Product> productOptional = repo.findById(id);
	    if (productOptional.isPresent()) {
	        ProductDto productDto = mapper.map(productOptional.get(), ProductDto.class);
	        ApiResponse<ProductDto> response = new ApiResponse<>("Product found successfully", productDto);
	        return ResponseEntity.ok(response);
	    } else {
	        ApiResponse<ProductDto> response = new ApiResponse<>("Product with ID " + id + " not found", null);
	        return ResponseEntity.status(404).body(response);
	    }
	}


	@Override
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(Long id, ProductDto productDto) {
	    Product product = repo.findById(id).orElse(null);
	    if (product == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ApiResponse<>("Product with ID " + id + " not found.", null));
	    }
	    product.setName(productDto.getName());
	    product.setPrice(productDto.getPrice());
	    product.setQuantity(productDto.getQuantity());

	    Product updatedProduct = repo.save(product);
	    ProductDto updatedProductDto = mapper.map(updatedProduct, ProductDto.class);
	    return ResponseEntity.ok(new ApiResponse<>("Product updated successfully.", updatedProductDto));
	}


	@Override
	public String deleteById(Long id) {
		if (repo.findById(id).isPresent()) {
			repo.deleteById(id);
			return "Deleted successfully";
		}
		return "Product not found with id "+id;

	}

}
