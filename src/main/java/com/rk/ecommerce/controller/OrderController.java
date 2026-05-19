package com.rk.ecommerce.controller;

import com.rk.ecommerce.dto.OrderResponse;
import com.rk.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🔥 PLACE ORDER (SECURE - NO userId)
    @PostMapping("/place")
    public String placeOrder() {
        return orderService.placeOrderForLoggedInUser();
    }

    // 🔥 GET ORDER HISTORY (SECURE)
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {
    	System.out.println("API HIT ✅");
        return orderService.getOrdersForLoggedInUser();
    }
}