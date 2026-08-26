package com.smarthr.employeeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeaveRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Leave Type ID is required")
    private Long leaveTypeId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private String reason;
}
