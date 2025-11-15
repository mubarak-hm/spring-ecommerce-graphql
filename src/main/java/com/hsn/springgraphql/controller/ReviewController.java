
package com.hsn.springgraphql.controller;

import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.entity.User;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ProductService productService;

    @BatchMapping(typeName = "Review", field = "product")
    public Map<Review, Product> product(List<Review> reviews) {
        List<Long> productIds = reviews.stream().map(Review::getProductId).toList();

        Map<Long, Product> productMap = productService.getProductsByIds(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        return reviews.stream()
                .collect(Collectors.toMap(
                        review -> review,
                        review -> productMap.get(review.getProductId())
                ));
    }

    @BatchMapping(typeName = "Review", field = "user")
    public Map<Review, User> user(List<Review> reviews) {
        List<Long> userIds = reviews.stream().map(Review::getUserId).toList();

        Map<Long, User> userMap = productService.getUsersByIds(userIds)
                .stream().collect(Collectors.toMap(User::getId, u -> u));

        return reviews.stream()
                .collect(Collectors.toMap(
                        review -> review,
                        review -> userMap.get(review.getUserId())
                ));
    }
}
