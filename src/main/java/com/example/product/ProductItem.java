package com.example.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("PRODUCTS")
record ProductItem(
        @Id Long productId,
        String name,
        String description,
        String category,
        String brand,
        BigDecimal price
        ) {
        static final String SORT_PROPERTY_NAME = "name";
        static final String SORT_PROPERTY_PRICE = "price";
        static final String SORT_PROPERTY_DESCRIPTION = "description";
        static final String SORT_PROPERTY_CATEGORY = "category";
        static final String SORT_PROPERTY_BRAND = "brand";

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                ProductItem that = (ProductItem) o;
                return productId == that.productId;
        }
        @Override
        public int hashCode() {
                return Objects.hashCode(productId);
        }

}
