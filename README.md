# 🛒 MyStore Ecommerce Project

A full-stack Spring Boot + JWT + HTML/CSS/JS based Ecommerce application with role-based access (USER / ADMIN), cart system, and order management.

---

## 🚀 Features

### 👤 User Features
- Register & Login (JWT Authentication)
- Browse Products
- Add to Cart
- Remove from Cart
- Place Orders
- View Order History

### 🔐 Admin Features
- Add Products
- Update Products
- Delete Products
- Manage Inventory

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL

### Frontend
- HTML
- CSS
- JavaScript (Fetch API)

---

## 📂 Project Structure

```
src
└── main
└── java/com/rk/ecommerce
├── controller
│ ├── AuthController.java
│ ├── ProductController.java
│ ├── CartController.java
│ ├── OrderController.java
│
├── service
│ ├── UserService.java
│ ├── ProductService.java
│ ├── CartService.java
│ ├── OrderService.java
│
├── service/impl
│ ├── UserServiceImpl.java
│ ├── ProductServiceImpl.java
│ ├── CartServiceImpl.java
│ ├── OrderServiceImpl.java
│
├── entity
│ ├── User.java
│ ├── Product.java
│ ├── Cart.java
│ ├── CartItem.java
│ ├── Order.java
│ ├── OrderItem.java
│ ├── Role.java
│
├── repository
│ ├── UserRepository.java
│ ├── ProductRepository.java
│ ├── CartRepository.java
│ ├── CartItemRepository.java
│ ├── OrderRepository.java
│
├── dto
│ ├── LoginRequest.java
│ ├── RegisterRequest.java
│ ├── ProductRequest.java
│ ├── ProductResponse.java
│ ├── CartResponse.java
│ ├── OrderResponse.java
│
├── security
│ ├── JwtUtil.java
│ ├── JwtFilter.java
│ ├── SecurityConfig.java
│
└── EcommerceApplication.java
```
---

## 🔐 Authentication Flow

1. User registers → `/api/auth/register`
2. User logs in → `/api/auth/login`
3. JWT Token generated
4. Token used in all APIs:

Authorization: Bearer <token>

---

## 📦 API Endpoints

### Auth
- POST `/api/auth/register`
- POST `/api/auth/login`

### Products
- GET `/api/products`
- POST `/api/products` (ADMIN)
- PUT `/api/products/{id}` (ADMIN)
- DELETE `/api/products/{id}` (ADMIN)

### Cart
- GET `/api/cart/my`
- POST `/api/cart/add-secure`
- DELETE `/api/cart/remove-secure`

### Orders
- POST `/api/orders/place`
- GET `/api/orders/my`

---

## 🖥️ UI Pages

- `login.html`
- `register.html`
- `products.html`
- `cart.html`
- `orders.html`

---

## ▶️ How to Run Project

### Backend

mvn spring-boot:run

### Frontend

Open in browser:


http://localhost:8080/login.html

---

## 👨‍💻 Roles

| Role  | Access |
|------|--------|
| USER | Browse, Cart, Orders |
| ADMIN | Product Management + User features |

---

## 📌 Future Improvements

- Payment Gateway (Razorpay / Stripe)
- Product Search & Filter
- Pagination
- Image Upload for Products
- User Profile Page

---

## 🚀 Author

**Rakesh Patil**  
Full Stack Developer (Spring Boot + Frontend)
    
