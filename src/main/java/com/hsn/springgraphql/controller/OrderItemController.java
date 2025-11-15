
package com.hsn.springgraphql.controller;

import com.hsn.springgraphql.entity.OrderItem;
import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderItemController {

    private final ProductService productService;

    @BatchMapping(typeName = "OrderItem", field = "product")
    public Map<OrderItem, Product> product(List<OrderItem> items) {
        List<Long> productIds = items.stream().map(OrderItem::getProductId).toList();

        Map<Long, Product> productMap = productService.getProductsByIds(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        return items.stream()
                .collect(Collectors.toMap(
                        item -> item,
                        item -> productMap.get(item.getProductId())
                ));
    }
}