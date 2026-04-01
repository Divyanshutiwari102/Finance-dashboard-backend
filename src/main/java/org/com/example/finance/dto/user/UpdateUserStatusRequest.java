package org.com.example.finance.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    @NotNull
    private Boolean active;
}