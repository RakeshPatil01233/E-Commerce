package com.rk.ecommerce.repository;

import com.rk.ecommerce.entity.Order;
import com.rk.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
   
}