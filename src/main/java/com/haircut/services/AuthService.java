package com.haircut.services;

import com.haircut.dto.LoginRequest;
import com.haircut.dto.RegisterRequest;
import com.haircut.models.User;
import com.haircut.utils.ApiResponse;

public interface AuthService {
    ApiResponse<User> register(RegisterRequest request);
    ApiResponse<User> login(LoginRequest request);
    ApiResponse<Void> logout();
} 