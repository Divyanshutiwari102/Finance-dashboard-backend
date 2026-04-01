package org.com.example.finance.controller;

import org.com.example.finance.dto.dashboard.DashboardSummaryResponse;
import org.com.example.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public ResponseEntity<DashboardSummaryResponse> summary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
}