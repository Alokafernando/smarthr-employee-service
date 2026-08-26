package com.smarthr.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private BigDecimal basicSalary;
    private BigDecimal allowances;
    private BigDecimal deductions;
    private BigDecimal netSalary;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
