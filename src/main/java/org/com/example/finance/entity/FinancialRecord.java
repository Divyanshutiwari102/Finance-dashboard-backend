package org.com.example.finance.entity;

import org.com.example.finance.entity.enums.RecordType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_records", indexes = {
        @Index(name = "idx_record_date", columnList = "record_date"),
        @Index(name = "idx_record_type", columnList = "type"),
        @Index(name = "idx_record_category", columnList = "category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RecordType type;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "record_date", nullable = false)
    private LocalDate date;

    @Column(length = 500)
    private String notes;
    // add these fields
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = java.time.LocalDateTime.now();
    }
}