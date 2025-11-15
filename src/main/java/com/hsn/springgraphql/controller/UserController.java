package com.hsn.springgraphql.controller;

import com.hsn.springgraphql.entity.Order;
import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.entity.User;
import com.hsn.springgraphql.service.OrderService;
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
public class UserController {

    private final OrderService orderService;
    private final ProductService productService;

    @BatchMapping(typeName = "User", field = "Orders")
    public Map<User, List<Order>> orders(List<User> users) {
        List<Long> userIds = users.stream().map(User::getId).toList();

        List<Order> allOrders = orderService.getOrdersForUsers(userIds);

        Map<Long, List<Order>> ordersByUserId = allOrders.stream()
                .collect(Collectors.groupingBy(Order::getUserId));

        return users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> ordersByUserId.getOrDefault(user.getId(), Collections.emptyList())
                ));
    }

    @BatchMapping(typeName = "User", field = "reviews")
    public Map<User, List<Review>> reviews(List<User> users) {
        List<Long> userIds = users.stream().map(User::getId).toList();

        List<Review> allReviews = productService.getReviewsForUsers(userIds);

        Map<Long, List<Review>> reviewsByUserId = allReviews.stream()
                .collect(Collectors.groupingBy(Review::getUserId));

        return users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> reviewsByUserId.getOrDefault(user.getId(), Collections.emptyList())
                ));
    }
}