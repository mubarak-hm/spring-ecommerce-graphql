package com.hsn.springgraphql.controller;


import com.hsn.springgraphql.entity.Product;
import com.hsn.springgraphql.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private  final ProductService productService;
    @QueryMapping()
    public Product  product(@Argument Long id)  {
        return  productService.getProduct(id);
    }

    @QueryMapping
    public List<Product> products (){
        return productService.getAllProducts();
    }

}
