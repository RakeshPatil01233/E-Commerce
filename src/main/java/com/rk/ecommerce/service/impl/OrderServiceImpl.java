package com.rk.ecommerce.service.impl;

import com.rk.ecommerce.entity.*;
import com.rk.ecommerce.repository.*;
import com.rk.ecommerce.security.SecurityUtil;
import com.rk.ecommerce.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.rk.ecommerce.dto.OrderResponse;
import com.rk.ecommerce.dto.OrderItemResponse;
import com.rk.ecommerce.repository.CartItemRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public String placeOrder(Long userId) {

        // 🔥 1. Get User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 2. Get Cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔥 3. Create Order
        Order order = new Order();
        order.setUser(user);

        double totalAmount = 0;

        // 🔥 4. Convert CartItems → OrderItems
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            return orderItem;

        }).toList();

        // 🔥 5. Calculate Total
        for (OrderItem item : orderItems) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        // 🔥 6. Save Order
        orderRepository.save(order);

        // 🔥 7. Clear Cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return "Order placed successfully";
    }
    
    @Override
    public List<OrderResponse> getUserOrders(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> {

            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setTotalAmount(order.getTotalAmount());

            List<OrderItemResponse> items = order.getItems().stream().map(item -> {
                OrderItemResponse itemResponse = new OrderItemResponse();
                itemResponse.setProductName(item.getProduct().getName());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPrice(item.getPrice());
                return itemResponse;
            }).toList();

            response.setItems(items);

            return response;

        }).toList();
    }
    
    @Override
    public String placeOrderForLoggedInUser() {

        String email = SecurityUtil.getLoggedInUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {

            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            item.setOrder(order);

            return item;

        }).toList();

        order.setItems(orderItems);

        orderRepository.save(order);

        // 🔥 CLEAR CART AFTER ORDER
        cartItemRepository.deleteAll(cart.getItems());

        return "Order placed successfully";
    }
    
    
    @Override
    public List<OrderResponse> getOrdersForLoggedInUser() {

        String email = SecurityUtil.getLoggedInUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> {

            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setStatus(order.getStatus());

            List<OrderItemResponse> items = order.getItems().stream().map(item -> {
                OrderItemResponse ir = new OrderItemResponse();
                ir.setProductName(item.getProduct().getName());
                ir.setPrice(item.getPrice());
                ir.setQuantity(item.getQuantity());
                return ir;
            }).toList();

            response.setItems(items);

            double total = items.stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .sum();

            response.setTotalAmount(total);

            return response;

        }).toList();
    }
    
}