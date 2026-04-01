package org.com.example.finance.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String tokenType;
    private Long expiresInMs;
}