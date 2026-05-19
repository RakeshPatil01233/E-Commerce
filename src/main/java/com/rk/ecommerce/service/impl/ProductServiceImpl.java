package com.rk.ecommerce.service.impl;

import com.rk.ecommerce.entity.Product;
import com.rk.ecommerce.repository.ProductRepository;
import com.rk.ecommerce.service.ProductService;
import com.rk.ecommerce.dto.ProductRequest;
import com.rk.ecommerce.dto.ProductResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 🔥 Common mapper (VERY IMPORTANT)
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        return response;
    }

    // 🔥 CREATE
    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    // 🔥 GET ALL
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 GET BY ID
    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    // 🔥 UPDATE
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setQuantity(request.getQuantity());

        Product updated = productRepository.save(existing);

        return mapToResponse(updated);
    }

    // 🔥 DELETE
    @Override
    public void deleteProduct(Long id) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(existing);
    }
}