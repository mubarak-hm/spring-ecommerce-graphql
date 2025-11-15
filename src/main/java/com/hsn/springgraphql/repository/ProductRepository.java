package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findByCategoryIdIn(List<Long> categoryIds);
}
