package com.hsn.springgraphql.controller;


import com.hsn.springgraphql.dto.CreateCategoryRequest;
import com.hsn.springgraphql.dto.CreateProductRequest;
import com.hsn.springgraphql.entity.Category;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.entity.User;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @QueryMapping()
    public Product product(@Argument String id) {
        return productService.getProduct(Long.parseLong(id));
    }

    @BatchMapping(typeName = "Product", field = "category")
    public Map<Product, Category> getCategoryForProduct(List<Product> products) {

        Map<Long, Category> categoryMap = productService.getProductCategory(products)
                .stream().collect(Collectors.toMap(Category::getId, category -> category));
        return products.stream()
                .collect(Collectors.toMap
                        (product -> product, product -> categoryMap.get(product.getCategoryId())));

    }


    @QueryMapping
    public List<Category> categories() {
        return productService.getAllCategories();
    }

    @QueryMapping
    public List<Product> products() {
        return productService.getAllProducts();
    }


    @MutationMapping
    public Product createProduct(@Argument CreateProductRequest input) {
        return productService.createProduct(input);
    }

    @MutationMapping
    Category createCategory(@Argument CreateCategoryRequest input) {
        return productService.createNewCategory(input);
    }


    @BatchMapping(typeName = "Product")
    public Map<Product, List<Review>> reviews(List<Product> products) {
        List<Long> productIds = products.stream()
                .map(Product::getId)
                .distinct()
                .toList();
        List<Review> reviews = productService.getReviewsForProducts(productIds);
        Map<Long, List<Review>> reviewsByProductId = reviews.stream()
                .collect(Collectors.groupingBy(Review::getProductId));

        return products.stream()
                .collect(Collectors.toMap(product -> product, product ->
                        reviewsByProductId.getOrDefault(product.getId(), Collections.emptyList())));
    }


    @BatchMapping(typeName = "Product", field = "Seller")
    public Map<Product, User> seller(List<Product> products) {
        List<Long> sellerIds = products.stream()
                .map(Product::getSellerId)
                .distinct()
                .toList();

        Map<Long, User> userMap = productService.getSellersByIds(sellerIds)
                .stream().collect(Collectors.toMap(User::getId, user -> user));

        return products.stream()
                .collect(Collectors.toMap(
                        product -> product,
                        product -> userMap.get(product.getSellerId())
                ));
    }


    @BatchMapping(typeName = "Product", field = "averageRating")
    public Map<Product, Double> averageRating(List<Product> products) {
        List<Long> productIds = products.stream()
                .map(Product::getId)
                .distinct()
                .toList();

        Map<Long, Double> averageMap = productService.getAverageRatings(productIds);
        return products.stream()
                .collect(Collectors.toMap(
                        product -> product,
                        product -> averageMap.getOrDefault(product.getId(), 0.0)
                ));
    }


}
