package com.asusoftware.userapi.product.controller;

import com.asusoftware.userapi.product.model.Product;
import com.asusoftware.userapi.product.model.dto.CreateProductDto;
import com.asusoftware.userapi.product.model.dto.ProductDto;
import com.asusoftware.userapi.product.service.ProductService;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/findAll")
    public List<ProductDto> findAll() {
        return null;
    }

    @PostMapping(path = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createProduct(@ModelAttribute CreateProductDto createProductDto,
                                                 @RequestParam("images") MultipartFile[] imageFiles) throws Exception {
        // This uses the previously discussed method for uploading multiple images with a product
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductWithImages(createProductDto, imageFiles));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProducts(pageable);

        List<ProductDto> productDTOs = productPage.getContent().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Path filePath = Paths.get("uploads/products/" + filename);
        Resource fileResource = new FileSystemResource(filePath);

        if (fileResource.exists() || fileResource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                    .body(fileResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
