package com.CategoryByProducts.CategoryByProduct.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private Long categoryId; // For associating the category
}

