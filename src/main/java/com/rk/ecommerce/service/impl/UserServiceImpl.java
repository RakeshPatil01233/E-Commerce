package com.rk.ecommerce.service.impl;

import com.rk.ecommerce.entity.User;
import com.rk.ecommerce.entity.Role;
import com.rk.ecommerce.repository.UserRepository;
import com.rk.ecommerce.service.UserService;
import com.rk.ecommerce.dto.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(RegisterRequest request) {

        // 🔥 Check duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // 🔥 VERY IMPORTANT: FORCE USER ROLE
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}