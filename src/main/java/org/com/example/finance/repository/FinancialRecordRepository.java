package org.com.example.finance.repository;

import org.com.example.finance.entity.FinancialRecord;
import org.com.example.finance.entity.enums.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, JpaSpecificationExecutor<FinancialRecord> {

    @Query("select coalesce(sum(fr.amount), 0) from FinancialRecord fr where fr.type = :type")
    BigDecimal sumByType(@Param("type") RecordType type);

    @Query("""
            select fr.category as category, coalesce(sum(fr.amount), 0) as total
            from FinancialRecord fr
            group by fr.category
            order by total desc
            """)
    List<Object[]> categoryWiseTotals();

    @Query("""
            select year(fr.date) as yr, month(fr.date) as mon,
                   coalesce(sum(case when fr.type = 'INCOME' then fr.amount else 0 end), 0) as income,
                   coalesce(sum(case when fr.type = 'EXPENSE' then fr.amount else 0 end), 0) as expense
            from FinancialRecord fr
            group by year(fr.date), month(fr.date)
            order by year(fr.date), month(fr.date)
            """)
    List<Object[]> monthlyTrends();

    Page<FinancialRecord> findByDateBetween(LocalDate from, LocalDate to, Pageable pageable);
}