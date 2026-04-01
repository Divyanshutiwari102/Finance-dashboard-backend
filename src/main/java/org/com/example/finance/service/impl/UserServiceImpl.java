package org.com.example.finance.service.impl;

import org.com.example.finance.dto.auth.RegisterUserRequest;
import org.com.example.finance.dto.user.UserResponse;
import org.com.example.finance.entity.*;
import org.com.example.finance.entity.enums.UserRole;
import org.com.example.finance.exception.*;
import org.com.example.finance.repository.*;
import org.com.example.finance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ForbiddenOperationException("Email already exists");
        }
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(role)
                .build();

        return toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse updateUserStatus(Long id, boolean active) {
        User user = getUser(id);
        user.setActive(active);
        return toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserRole(Long id, UserRole roleName) {
        User user = getUser(id);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role);
        return toResponse(userRepository.save(user));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .active(user.isActive())
                .role(user.getRole().getName())
                .build();
    }
}