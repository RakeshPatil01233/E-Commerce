package com.rk.ecommerce.service;
import com.rk.ecommerce.dto.CartResponse;


public interface CartService {

    String addToCart(Long userId, Long productId, int quantity);
    CartResponse getCart(Long userId);
    void removeFromCart(Long userId, Long productId);
    void updateQuantity(Long userId, Long productId, int quantity);
    CartResponse getCartForLoggedInUser();
    String addToCartForLoggedInUser(Long productId, int quantity);

    void removeFromCartForLoggedInUser(Long productId);

    void updateQuantityForLoggedInUser(Long productId, int quantity);
}
