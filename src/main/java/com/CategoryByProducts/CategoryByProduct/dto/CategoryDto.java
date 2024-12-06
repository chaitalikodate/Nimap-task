package com.CategoryByProducts.CategoryByProduct.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CategoryDto {
    private Long id;
    private String name;
    private List<ProductDto> products;
}
