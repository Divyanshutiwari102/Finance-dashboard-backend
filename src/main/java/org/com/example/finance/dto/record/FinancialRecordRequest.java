package org.com.example.finance.dto.record;

import org.com.example.finance.entity.enums.RecordType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FinancialRecordRequest {
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private RecordType type;

    @NotBlank
    @Size(max = 100)
    private String category;

    @NotNull
    private LocalDate date;

    @Size(max = 500)
    private String notes;
}