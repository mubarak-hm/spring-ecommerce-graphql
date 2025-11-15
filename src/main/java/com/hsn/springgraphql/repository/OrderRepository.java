package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserIdIn(List<Long> userIds);
}
