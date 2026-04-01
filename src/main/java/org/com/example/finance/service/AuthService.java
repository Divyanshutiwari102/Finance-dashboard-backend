package org.com.example.finance.service;

import org.com.example.finance.dto.auth.*;

public interface AuthService {
    void register(RegisterUserRequest request);
    LoginResponse login(LoginRequest request);
}