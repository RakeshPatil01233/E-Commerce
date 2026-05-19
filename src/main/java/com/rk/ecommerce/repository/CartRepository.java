package com.rk.ecommerce.repository;

import com.rk.ecommerce.entity.Cart;
import com.rk.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 🔥 Custom method
    Optional<Cart> findByUser(User user);
}