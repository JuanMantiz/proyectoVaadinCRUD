package com.example.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

interface ProductItemRepository extends PagingAndSortingRepository<ProductItem, Long> {

    Optional<ProductItem> findById(Long id);
    Slice<ProductItem> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
