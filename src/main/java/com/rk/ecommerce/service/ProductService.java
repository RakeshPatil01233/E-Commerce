package com.rk.ecommerce.service;

import com.rk.ecommerce.dto.ProductRequest;
import com.rk.ecommerce.dto.ProductResponse;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}