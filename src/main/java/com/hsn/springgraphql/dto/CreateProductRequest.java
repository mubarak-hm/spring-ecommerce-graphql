package com.hsn.springgraphql.dto;

public record CreateProductRequest(
        String name,
        String description,
        double price,
        int stock,
        String categoryId
) { }

