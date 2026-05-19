package com.rk.ecommerce.service;

import com.rk.ecommerce.dto.RegisterRequest;
import com.rk.ecommerce.entity.User;

public interface UserService {

    User registerUser(RegisterRequest request);
}