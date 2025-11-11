package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review findByProductId(Long productId);
}
