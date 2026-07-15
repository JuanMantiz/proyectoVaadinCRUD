package com.example.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductDetailsRepository extends CrudRepository<ProductDetails, Long> {

}
