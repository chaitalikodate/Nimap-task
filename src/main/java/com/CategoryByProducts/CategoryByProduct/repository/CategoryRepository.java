package com.CategoryByProducts.CategoryByProduct.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CategoryByProducts.CategoryByProduct.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	public Optional<Category> findByName(String name);
}

