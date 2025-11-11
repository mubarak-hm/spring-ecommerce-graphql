package com.hsn.springgraphql.controller;


import com.hsn.springgraphql.dto.CreateCategoryRequest;
import com.hsn.springgraphql.dto.CreateProductRequest;
import com.hsn.springgraphql.entity.Category;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

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
                        (product -> product, product -> categoryMap.get(product.getCategory().getId())));

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


//    @SchemaMapping(typeName = "Product")
//    public List<Review> reviews(Product product) {
//        return productService.fetchReviewsForProduct(product);
//    }


}
