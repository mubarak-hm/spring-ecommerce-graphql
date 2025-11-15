package com.hsn.springgraphql.controller;


import com.hsn.springgraphql.dto.CreateOrderRequest;
import com.hsn.springgraphql.entity.Order;
import com.hsn.springgraphql.entity.OrderItem;
import com.hsn.springgraphql.entity.User;
import com.hsn.springgraphql.service.OrderService;
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

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @MutationMapping
    public Order createOrder(@Argument CreateOrderRequest input) {
        return orderService.createOrder(input);
    }


    @QueryMapping
    public List<Order> orders() {
        return orderService.getAllOrders();
    }


    @QueryMapping
    public Order order(@Argument String id) {
        return orderService.getSingleOrder(id);
    }




    @BatchMapping(typeName = "Order", field = "user")
    public Map<Order, User> user(List<Order> orders) {
        List<Long> userIds = orders.stream()
                .map(Order::getUserId)
                .distinct()
                .toList();

        Map<Long, User> userMap = orderService.getUsersByIds(userIds)
                .stream().collect(Collectors.toMap(User::getId, user -> user));

        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> userMap.get(order.getUserId())
                ));
    }

    @BatchMapping(typeName = "Order", field = "items")
    public Map<Order, List<OrderItem>> items(List<Order> orders) {
        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .distinct()
                .toList();

        List<OrderItem> allItems = orderService.getItemsForOrders(orderIds);

        Map<Long, List<OrderItem>> itemsByOrderId = allItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));

        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> itemsByOrderId.getOrDefault(order.getId(), Collections.emptyList())
                ));
    }
}



