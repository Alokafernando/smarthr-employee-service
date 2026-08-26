package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.DashboardStatsDto;
import com.smarthr.employeeservice.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Statistics", description = "Endpoints for aggregate HR metrics and dashboard summary")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/statistics")
    @Operation(summary = "Get aggregate statistics for the HR dashboard")
    public ResponseEntity<DashboardStatsDto> getDashboardStatistics() {
        return ResponseEntity.ok(dashboardService.getDashboardStatistics());
    }
}
