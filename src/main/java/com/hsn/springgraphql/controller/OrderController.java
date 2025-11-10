package com.hsn.springgraphql.controller;


import com.hsn.springgraphql.dto.CreateOrderRequest;
import com.hsn.springgraphql.entity.Order;
import com.hsn.springgraphql.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

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
}



