package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.auth.AuthRequest;
import com.ecommerce.auth_service.dto.auth.AuthResponse;
import com.ecommerce.auth_service.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);

    AuthResponse register(RegisterRequest request);
}