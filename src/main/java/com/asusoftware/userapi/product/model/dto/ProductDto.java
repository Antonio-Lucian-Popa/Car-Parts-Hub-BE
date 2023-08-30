package com.asusoftware.userapi.product.model.dto;

import com.asusoftware.userapi.product.model.Brand;
import com.asusoftware.userapi.product.model.Category;
import com.asusoftware.userapi.product.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDto {

    private UUID id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private Category category;
    private Brand brand;
    private List<String> images;

    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        return dto;
    }
}
