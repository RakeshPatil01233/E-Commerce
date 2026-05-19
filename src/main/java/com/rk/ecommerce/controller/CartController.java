package com.rk.ecommerce.controller;

import com.rk.ecommerce.dto.CartResponse;
import com.rk.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 🔥 Add to Cart API
    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId,
                            @RequestParam Long productId,
                            @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam Long userId,
                                 @RequestParam Long productId) {

        cartService.removeFromCart(userId, productId);
        return "Product removed from cart";
    }

    @PutMapping("/update")
    public String updateQuantity(@RequestParam Long userId,
                                 @RequestParam Long productId,
                                 @RequestParam int quantity) {

        cartService.updateQuantity(userId, productId, quantity);
        return "Cart updated successfully";
    }
    
    @GetMapping("/my")
    public CartResponse getMyCart() {
        return cartService.getCartForLoggedInUser();
    }
    
    @PostMapping("/add-secure")
    public String addToCartSecure(@RequestParam Long productId,
                                 @RequestParam int quantity) {

        return cartService.addToCartForLoggedInUser(productId, quantity);
    }


    // 🔥 Remove from Cart (logged-in user)
    @DeleteMapping("/remove-secure")
    public String removeFromCartSecure(@RequestParam Long productId) {

        cartService.removeFromCartForLoggedInUser(productId);
        return "Product removed";
    }


    // 🔥 Update quantity (logged-in user)
    @PutMapping("/update-secure")
    public String updateQuantitySecure(@RequestParam Long productId,
                                       @RequestParam int quantity) {

        cartService.updateQuantityForLoggedInUser(productId, quantity);
        return "Updated";
    }
    
}