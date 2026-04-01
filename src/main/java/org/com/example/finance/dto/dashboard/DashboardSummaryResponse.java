package org.com.example.finance.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardSummaryResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private List<CategoryTotalResponse> categoryTotals;
    private List<MonthlyTrendResponse> monthlyTrends;
}