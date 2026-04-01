package org.com.example.finance.service;

import org.com.example.finance.dto.auth.RegisterUserRequest;
import org.com.example.finance.dto.user.*;
import java.util.List;

public interface UserService {
    UserResponse createUser(RegisterUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse updateUserStatus(Long id, boolean active);
    UserResponse updateUserRole(Long id, org.com.example.finance.entity.enums.UserRole role);
}