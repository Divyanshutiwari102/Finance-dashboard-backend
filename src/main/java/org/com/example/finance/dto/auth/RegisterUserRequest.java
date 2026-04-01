package org.com.example.finance.dto.auth;

import org.com.example.finance.entity.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotNull
    private UserRole role;
}