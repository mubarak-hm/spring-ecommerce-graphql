package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Review;
import com.hsn.springgraphql.interfaces.RatingAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
   List<Review> findByProductIdIn(List<Long> productIds);
    List<Review> findByUserIdIn(List<Long> userIds);
    @Query("SELECT r.productId AS productId, AVG(r.rating) AS average " +
            "FROM Review r " +
            "WHERE r.productId IN :productIds " +
            "GROUP BY r.productId")
    List<RatingAverage> findAverageRatings(@Param("productIds") List<Long> productIds);
}
