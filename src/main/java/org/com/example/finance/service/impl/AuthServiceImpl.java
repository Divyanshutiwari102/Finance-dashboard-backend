package org.com.example.finance.service.impl;

import org.com.example.finance.dto.auth.*;
import org.com.example.finance.entity.*;
import org.com.example.finance.exception.ForbiddenOperationException;
import org.com.example.finance.repository.*;
import org.com.example.finance.security.JwtService;
import org.com.example.finance.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final org.com.example.finance.security.CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public void register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ForbiddenOperationException("Email already exists");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ForbiddenOperationException("Role not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(role)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresInMs(jwtService.getExpirationMs())
                .build();
    }
}