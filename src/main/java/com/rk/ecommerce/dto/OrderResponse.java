package com.rk.ecommerce.dto;

import java.util.List;

public class OrderResponse {

    private Long id;
    private String status;
    private double totalAmount;

    private List<OrderItemResponse> items;

    // -------- GETTERS --------

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    // -------- SETTERS --------

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}