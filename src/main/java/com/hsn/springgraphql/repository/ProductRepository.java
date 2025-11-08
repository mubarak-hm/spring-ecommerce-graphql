package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Long> {

}
