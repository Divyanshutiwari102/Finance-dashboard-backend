package org.com.example.finance.dto.user;

import org.com.example.finance.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private boolean active;
    private UserRole role;
}