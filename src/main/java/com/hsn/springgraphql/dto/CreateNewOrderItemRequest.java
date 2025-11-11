package com.hsn.springgraphql.dto;

public record CreateNewOrderItemRequest(String productId, Integer quantity) {
}
