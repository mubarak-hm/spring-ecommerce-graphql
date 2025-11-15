package com.hsn.springgraphql.service;

import com.hsn.springgraphql.dto.CreateCategoryRequest;
import com.hsn.springgraphql.dto.CreateProductRequest;
import com.hsn.springgraphql.entity.Category;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.repository.CategoryRepository;
import com.hsn.springgraphql.repository.ProductRepository;
import com.hsn.springgraphql.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("product not found");
        }
        return optionalProduct.get();
    }


    public Category createNewCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getProductCategory(List<Product> products) {

        List<Long> categoryIds = products.stream()
                .map(Product::getCategoryId)
                .distinct()
                .toList();
        return categoryRepository.findAllById(categoryIds);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product createProduct(CreateProductRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(Long.parseLong(request.categoryId()));
        if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException("category not found");

        }
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .description(request.description())
                .category(optionalCategory.get())
                .build();
        return productRepository.save(product);
    }

    public List<Review> getReviewsForProducts(List<Long> productIds){
        return   reviewRepository.findByProductIdIn(productIds);
    }

}
