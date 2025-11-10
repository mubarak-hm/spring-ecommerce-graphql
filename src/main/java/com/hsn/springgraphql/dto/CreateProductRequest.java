package com.hsn.springgraphql.dto;

public record CreateProductRequest(
        String productName,
        String description,
        double price,
        int stock,
        String categoryId
) { }

