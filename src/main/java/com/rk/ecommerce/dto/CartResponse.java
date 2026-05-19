package com.rk.ecommerce.dto;

import java.util.List;

public class CartResponse {

    private Long userId;
    private List<CartItemResponse> items;

    // Getters

    public Long getUserId() {
        return userId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    // Setters

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}
