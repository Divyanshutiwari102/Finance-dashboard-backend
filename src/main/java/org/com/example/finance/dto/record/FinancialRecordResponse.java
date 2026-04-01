package org.com.example.finance.dto.record;

import org.com.example.finance.entity.enums.RecordType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FinancialRecordResponse {
    private Long id;
    private BigDecimal amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String notes;
}