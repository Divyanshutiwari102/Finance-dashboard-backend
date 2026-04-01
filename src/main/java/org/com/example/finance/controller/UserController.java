package org.com.example.finance.controller;

import org.com.example.finance.dto.auth.RegisterUserRequest;
import org.com.example.finance.dto.user.*;
import org.com.example.finance.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(userService.updateUserStatus(id, request.getActive()));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateRole(@PathVariable Long id, @Valid @RequestBody UpdateUserRoleRequest request) {
        return ResponseEntity.ok(userService.updateUserRole(id, request.getRole()));
    }
}