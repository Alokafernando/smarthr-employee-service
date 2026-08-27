package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.SalaryDto;
import com.smarthr.employeeservice.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salaries")
@RequiredArgsConstructor
@Tag(name = "Salary Information", description = "Endpoints for employee salary details and adjustments")
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    @Operation(summary = "Get all salary records")
    public ResponseEntity<List<SalaryDto>> getAllSalaries(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(salaryService.getAllSalaries(month, year));
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get salary details for an employee")
    public ResponseEntity<SalaryDto> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(salaryService.getSalaryByEmployeeId(employeeId));
    }

    @PostMapping
    @Operation(summary = "Update or set employee salary")
    public ResponseEntity<SalaryDto> saveSalary(
            @RequestBody SalaryDto salaryDto,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return ResponseEntity.ok(salaryService.saveSalary(salaryDto, loggedInUser));
    }
}