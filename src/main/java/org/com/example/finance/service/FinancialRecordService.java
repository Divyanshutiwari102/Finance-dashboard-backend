package org.com.example.finance.service;

import org.com.example.finance.dto.record.*;
import org.com.example.finance.entity.enums.RecordType;
import org.springframework.data.domain.*;

import java.time.LocalDate;

public interface FinancialRecordService {
    FinancialRecordResponse create(FinancialRecordRequest request);
    FinancialRecordResponse update(Long id, FinancialRecordRequest request);
    void delete(Long id);
    FinancialRecordResponse getById(Long id);
    Page<FinancialRecordResponse> getAll(
            LocalDate from,
            LocalDate to,
            String category,
            RecordType type,
            Pageable pageable
    );
}