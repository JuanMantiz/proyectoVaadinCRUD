package com.example.product;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDetails> findItems(String searchTerm,
                                                    Pageable pageable) {
        return productRepository
                .findByNameContainingIgnoreCase(searchTerm, pageable).getContent();
    }
    public Optional<ProductDetails> findDetailsById(Long id) {
        return productRepository.findById(id);
    }
    public Optional<ProductDetails> findItemById(Long id) {
        return productRepository.findById(id);
    }
    @Transactional
    public ProductDetails save(ProductDetails productDetails) {
        return productRepository.save(new ProductDetails(productDetails));
    }

    @Transactional
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }
}
