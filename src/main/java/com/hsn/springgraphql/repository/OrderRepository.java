package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
