// In src/main/java/com/hsn/springgraphql/controller/CategoryController.java
package com.hsn.springgraphql.controller;

import com.hsn.springgraphql.entity.Category;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final ProductService productService;

    @BatchMapping(typeName = "Category", field = "products")
    public Map<Category, List<Product>> products(List<Category> categories) {
        List<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .distinct()
                .toList();

        List<Product> allProducts = productService.getProductsForCategories(categoryIds);

        Map<Long, List<Product>> productsByCategoryId = allProducts.stream()
                .collect(Collectors.groupingBy(Product::getCategoryId));

        return categories.stream()
                .collect(Collectors.toMap(
                        category -> category,
                        category -> productsByCategoryId.getOrDefault(category.getId(), Collections.emptyList())
                ));
    }
}
