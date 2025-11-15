package com.hsn.springgraphql.service;


import com.hsn.springgraphql.dto.CreateNewOrderItemRequest;
import com.hsn.springgraphql.dto.CreateOrderRequest;
import com.hsn.springgraphql.entity.Order;
import com.hsn.springgraphql.entity.OrderItem;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.entity.User;
import com.hsn.springgraphql.enums.OrderStatus;
import com.hsn.springgraphql.repository.OrderItemRepository;
import com.hsn.springgraphql.repository.OrderRepository;
import com.hsn.springgraphql.repository.ProductRepository;
import com.hsn.springgraphql.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public Order createOrder(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setPaymentMethod(request.paymentMethod());
        newOrder.setStatus(OrderStatus.PENDING);
        double total = 0.0;

        for (CreateNewOrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(Long.parseLong(itemRequest.productId()))
                    .orElseThrow(() -> new EntityNotFoundException(" product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            double subtotal = itemRequest.quantity() * product.getPrice();
            orderItem.setSubtotal(subtotal);
            total += subtotal;
            orderItem.setOrder(newOrder);
            newOrder.getItems().add(orderItem);
        }
        newOrder.setTotal(total);
        return orderRepository.save(newOrder);

    }

    public Order getSingleOrder(String id) {
        return orderRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }


    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public List<OrderItem> getItemsForOrders(List<Long> orderIds) {
        return orderItemRepository.findByOrderIdIn(orderIds);
    }

    public List<Order> getOrdersForUsers(List<Long> userIds) {
        return orderRepository.findByUserIdIn(userIds);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();

    }

}
