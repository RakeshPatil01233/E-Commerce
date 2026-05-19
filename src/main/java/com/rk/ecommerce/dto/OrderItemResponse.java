package com.rk.ecommerce.dto;

public class OrderItemResponse {

    private String productName;
    private double price;
    private int quantity;

    // getters
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // setters
    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}