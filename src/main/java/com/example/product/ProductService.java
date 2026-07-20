package com.example.product;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductItemRepository productCatalogItemRepository;
    private final ProductDetailsRepository productDetailsRepository;
    public ProductService(
            ProductItemRepository productCatalogItemRepository,
            ProductDetailsRepository productDetailsRepository) {
        this.productCatalogItemRepository = productCatalogItemRepository;
        this.productDetailsRepository = productDetailsRepository;
    }
    public List<ProductItem> findItems(String searchTerm,
                                                    Pageable pageable) {
        return productCatalogItemRepository
                .findByNameContainingIgnoreCase(searchTerm, pageable).getContent();
    }
    public Optional<ProductDetails> findDetailsById(Long id) {
        return productDetailsRepository.findById(id);
    }
    public Optional<ProductItem> findItemById(Long id) {
        return productCatalogItemRepository.findById(id);
    }
    @Transactional
    public ProductDetails save(ProductDetails productDetails) {
        return productDetailsRepository.save(new ProductDetails(productDetails));
    }

    @Transactional
    public void delete(Long productId) {
        productDetailsRepository.deleteById(productId);
    }
}
