package com.CategoryByProducts.CategoryByProduct.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CategoryByProducts.CategoryByProduct.entity.Category;
import com.CategoryByProducts.CategoryByProduct.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> findByNameAndQuantityAndPrice(String name,int quantity,double price);
}
