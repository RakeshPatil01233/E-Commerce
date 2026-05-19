package com.rk.ecommerce.service;

import com.rk.ecommerce.dto.OrderResponse;
import java.util.List;

public interface OrderService {

    String placeOrder(Long userId);
    List<OrderResponse> getUserOrders(Long userId);
    String placeOrderForLoggedInUser();

    List<OrderResponse> getOrdersForLoggedInUser();
}
