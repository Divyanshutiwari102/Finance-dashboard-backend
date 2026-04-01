package org.com.example.finance.service.impl;

import org.com.example.finance.dto.dashboard.*;
import org.com.example.finance.entity.enums.RecordType;
import org.com.example.finance.repository.FinancialRecordRepository;
import org.com.example.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordRepository recordRepository;

    @Override
    public DashboardSummaryResponse getSummary() {
        BigDecimal income = recordRepository.sumByType(RecordType.INCOME);
        BigDecimal expense = recordRepository.sumByType(RecordType.EXPENSE);
        BigDecimal net = income.subtract(expense);

        List<CategoryTotalResponse> categoryTotals = recordRepository.categoryWiseTotals()
                .stream()
                .map(r -> new CategoryTotalResponse((String) r[0], (BigDecimal) r[1]))
                .toList();

        List<MonthlyTrendResponse> monthlyTrends = recordRepository.monthlyTrends()
                .stream()
                .map(r -> {
                    Integer year = (Integer) r[0];
                    Integer month = (Integer) r[1];
                    BigDecimal monthIncome = (BigDecimal) r[2];
                    BigDecimal monthExpense = (BigDecimal) r[3];
                    return new MonthlyTrendResponse(
                            year + "-" + Month.of(month).name(),
                            monthIncome,
                            monthExpense,
                            monthIncome.subtract(monthExpense)
                    );
                }).toList();

        return DashboardSummaryResponse.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(net)
                .categoryTotals(categoryTotals)
                .monthlyTrends(monthlyTrends)
                .build();
    }
}