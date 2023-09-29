package com.asusoftware.userapi.product.service;

import com.asusoftware.userapi.product.model.Brand;
import com.asusoftware.userapi.product.model.Category;
import com.asusoftware.userapi.product.model.Product;
import com.asusoftware.userapi.product.model.dto.CreateProductDto;
import com.asusoftware.userapi.product.model.dto.ProductDto;
import com.asusoftware.userapi.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final String UPLOAD_DIR = "uploads/products/";

    /**
     * Retrieves paginated products.
     *
     * @param pageable Pagination and sorting information.
     * @return A page of products.
     */
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product saveProductWithImages(CreateProductDto productDto, MultipartFile[] imageFiles) throws Exception {
        Product product = CreateProductDto.fromDto(productDto);
        if (imageFiles != null && imageFiles.length > 0) {
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                String fileName = saveImage(file);
                fileNames.add(fileName);
            }
            product.setImagePathsList(fileNames);
        }

        return productRepository.save(product);
    }

    private String saveImage(MultipartFile file) throws Exception {
        try {
            byte[] bytes = file.getBytes();
            String modifiedFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path directory = Paths.get(UPLOAD_DIR);
            Files.createDirectories(directory); // Ensure the directory exists
            Path path = directory.resolve(modifiedFileName);
            Files.write(path, bytes);
            return modifiedFileName;
        } catch (IOException e) {
            throw new Exception("Failed to save image: " + e.getMessage());
        }
    }

    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.fromEntity(product);
    }

    public Page<Product> getProductsByCategory(Pageable pageable, List<String> categories, List<String> brands) {
        /* List<String> categoryNames = categories.stream()
                .map(Enum::name)
                .toList();

        List<String> brandNames = brands.stream()
                .map(Enum::name)
                .toList(); */

        return productRepository.findAllByCategoriesAndBrands(pageable, categories, brands);
    }
}
