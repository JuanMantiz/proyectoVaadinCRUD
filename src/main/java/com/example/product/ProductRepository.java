package com.example.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDetails, Long> {
    Page<ProductDetails> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
