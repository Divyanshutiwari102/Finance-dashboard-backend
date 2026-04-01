package org.com.example.finance.controller;

import org.com.example.finance.dto.record.*;
import org.com.example.finance.entity.enums.RecordType;
import org.com.example.finance.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecordResponse> create(@Valid @RequestBody FinancialRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecordResponse> update(@PathVariable Long id, @Valid @RequestBody FinancialRecordRequest request) {
        return ResponseEntity.ok(recordService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public ResponseEntity<FinancialRecordResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public ResponseEntity<Page<FinancialRecordResponse>> getAll(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "type", required = false) RecordType type,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return ResponseEntity.ok(recordService.getAll(from, to, category, type, pageable));
    }
}