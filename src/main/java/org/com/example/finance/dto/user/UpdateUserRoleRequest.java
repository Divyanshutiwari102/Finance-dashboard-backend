package org.com.example.finance.dto.user;

import org.com.example.finance.entity.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleRequest {
    @NotNull
    private UserRole role;
}