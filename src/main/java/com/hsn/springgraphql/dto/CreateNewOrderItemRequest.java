package com.hsn.springgraphql.dto;

public record CreateNewOrderItemRequest(String ProductId, Integer quantity) {
}
