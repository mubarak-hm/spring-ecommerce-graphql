package com.hsn.springgraphql.repository;


import com.hsn.springgraphql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}