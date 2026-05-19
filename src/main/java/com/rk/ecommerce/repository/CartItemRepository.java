package com.rk.ecommerce.repository;

import com.rk.ecommerce.entity.Cart;
import com.rk.ecommerce.entity.CartItem;
import com.rk.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 🔥 THIS IS WHAT YOU ARE MISSING
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}