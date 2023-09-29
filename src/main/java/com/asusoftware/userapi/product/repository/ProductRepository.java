package com.asusoftware.userapi.product.repository;

import com.asusoftware.userapi.product.model.Brand;
import com.asusoftware.userapi.product.model.Category;
import com.asusoftware.userapi.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:categories IS NULL OR p.category IN :categories) " +
            "AND (:brands IS NULL OR p.brand IN :brands)")
    Page<Product> findAllByCategoriesAndBrands(
            Pageable pageable,
            @Param("categories") List<String> categories,
            @Param("brands") List<String> brands
    );


}
