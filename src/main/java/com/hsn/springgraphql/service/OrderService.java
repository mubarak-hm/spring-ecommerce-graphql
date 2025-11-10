package com.hsn.springgraphql.service;


import com.hsn.springgraphql.dto.CreateNewOrderItemRequest;
import com.hsn.springgraphql.dto.CreateOrderRequest;
import com.hsn.springgraphql.entity.Order;
import com.hsn.springgraphql.entity.OrderItem;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.enums.OrderStatus;
import com.hsn.springgraphql.repository.OrderRepository;
import com.hsn.springgraphql.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setPaymentMethod(request.PaymentMethod());
        newOrder.setStatus(OrderStatus.PENDING);
        double total = 0.0;

        for (CreateNewOrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(Long.parseLong(itemRequest.ProductId()))
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

}
