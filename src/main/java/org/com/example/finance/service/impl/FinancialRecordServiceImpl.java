package org.com.example.finance.service.impl;

import org.com.example.finance.dto.record.*;
import org.com.example.finance.entity.FinancialRecord;
import org.com.example.finance.entity.enums.RecordType;
import org.com.example.finance.exception.ResourceNotFoundException;
import org.com.example.finance.repository.FinancialRecordRepository;
import org.com.example.finance.service.FinancialRecordService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository recordRepository;

    @Override
    public FinancialRecordResponse create(FinancialRecordRequest request) {
        FinancialRecord entity = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .notes(request.getNotes())
                .build();
        return toResponse(recordRepository.save(entity));
    }

    @Override
    public FinancialRecordResponse update(Long id, FinancialRecordRequest request) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());

        return toResponse(recordRepository.save(record));
    }

    @Override
    public void delete(Long id) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        recordRepository.delete(record);
    }

    @Override
    public FinancialRecordResponse getById(Long id) {
        return toResponse(recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found")));
    }

    @Override
    public Page<FinancialRecordResponse> getAll(LocalDate from, LocalDate to, String category, RecordType type, Pageable pageable) {
        Specification<FinancialRecord> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (from != null) predicates.add(cb.greaterThanOrEqualTo(root.get("date"), from));
            if (to != null) predicates.add(cb.lessThanOrEqualTo(root.get("date"), to));
            if (category != null && !category.isBlank()) predicates.add(cb.equal(root.get("category"), category));
            if (type != null) predicates.add(cb.equal(root.get("type"), type));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return recordRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private FinancialRecordResponse toResponse(FinancialRecord r) {
        return FinancialRecordResponse.builder()
                .id(r.getId())
                .amount(r.getAmount())
                .type(r.getType())
                .category(r.getCategory())
                .date(r.getDate())
                .notes(r.getNotes())
                .build();
    }
}