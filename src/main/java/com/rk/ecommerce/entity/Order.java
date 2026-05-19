package com.rk.ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Many orders belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔥 Total amount of order
    private double totalAmount;

    // 🔥 Order status (NEW FIELD)
    private String status;

    // 🔥 Auto timestamp
    private LocalDateTime createdAt;

    // 🔥 One order has many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    // 🔥 Automatically set time before saving
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    // ---------------- GETTERS ----------------

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // ---------------- SETTERS ----------------

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}