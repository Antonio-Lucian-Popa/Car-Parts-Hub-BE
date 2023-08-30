package com.asusoftware.userapi.product.model.dto;
import com.asusoftware.userapi.product.model.Brand;
import com.asusoftware.userapi.product.model.Category;
import com.asusoftware.userapi.product.model.Product;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDto {

        private String title;
        private String description;
        private Double price;
        private Integer quantity;
        private Category category;
        private Brand brand;

        public static Product fromDto(CreateProductDto productDto) {
                Product product = new Product();
                product.setTitle(productDto.getTitle());
                product.setDescription(productDto.getDescription());
                product.setPrice(productDto.getPrice());
                product.setQuantity(productDto.getQuantity());
                product.setCategory(productDto.getCategory());
                product.setBrand(productDto.getBrand());
                return product;
        }
}
