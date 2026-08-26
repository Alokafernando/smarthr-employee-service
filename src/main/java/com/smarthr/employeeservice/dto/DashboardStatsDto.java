package com.smarthr.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalEmployees;
    private long activeEmployees;
    private long totalDepartments;
    private long pendingLeaveRequests;
    private long todayPresentCount;
    private long todayLateCount;
    private long todayAbsentCount;
    private List<EmployeeDto> recentEmployees;
    private List<LeaveRequestDto> recentLeaves;
    private Map<String, Long> employeesByDepartment;
}
