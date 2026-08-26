package com.smarthr.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeNumber;
    private Long leaveTypeId;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private String approvedBy;
    private LocalDateTime createdAt;
}
