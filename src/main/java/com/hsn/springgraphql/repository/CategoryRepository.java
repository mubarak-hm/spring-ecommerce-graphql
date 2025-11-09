package com.hsn.springgraphql.repository;

import com.hsn.springgraphql.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    Category findByProducts(Long id);
}
