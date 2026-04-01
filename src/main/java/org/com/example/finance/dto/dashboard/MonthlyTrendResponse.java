package org.com.example.finance.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyTrendResponse {
    private String month;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal net;
}