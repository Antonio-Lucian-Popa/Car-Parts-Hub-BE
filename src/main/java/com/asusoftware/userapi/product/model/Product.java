package com.asusoftware.userapi.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Product")
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private Brand brand;

    // Store image paths as a comma-separated string
    @Column(name = "image_paths", length = 5000)  // Adjust length as needed
    private String imagePaths;

    public List<String> getImagePathsList() {
        if (this.imagePaths == null || this.imagePaths.isEmpty()) {
            return new ArrayList<>();
        }
        return Stream.of(this.imagePaths.split(","))
                .collect(Collectors.toList());
    }

    public void setImagePathsList(List<String> imagePathsList) {
        this.imagePaths = String.join(",", imagePathsList);
    }
}
