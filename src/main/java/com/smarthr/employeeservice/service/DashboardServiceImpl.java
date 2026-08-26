package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.DashboardStatsDto;
import com.smarthr.employeeservice.entity.Department;
import com.smarthr.employeeservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeService employeeService;
    private final LeaveService leaveService;

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStatistics() {
        long totalEmp = employeeRepository.count();
        long activeEmp = employeeRepository.countByEmploymentStatus("ACTIVE");
        long totalDepts = departmentRepository.count();
        long pendingLeaves = leaveRequestRepository.countByStatus("PENDING");

        LocalDate today = LocalDate.now();
        long presentCount = attendanceRepository.countByDateAndStatus(today, "PRESENT");
        long lateCount = attendanceRepository.countByDateAndStatus(today, "LATE");
        long absentCount = Math.max(0, activeEmp - (presentCount + lateCount));

        var recentEmployees = employeeService.getAllEmployees(
                PageRequest.of(0, 5, Sort.by("createdAt").descending())).getContent();

        var recentLeaves = leaveService.getAllLeaves(null).stream().limit(5).toList();

        Map<String, Long> byDept = new HashMap<>();
        List<Department> departments = departmentRepository.findAll();
        for (Department dept : departments) {
            long c = employeeRepository.countByDepartmentId(dept.getId());
            byDept.put(dept.getName(), c);
        }

        return DashboardStatsDto.builder()
                .totalEmployees(totalEmp)
                .activeEmployees(activeEmp)
                .totalDepartments(totalDepts)
                .pendingLeaveRequests(pendingLeaves)
                .todayPresentCount(presentCount)
                .todayLateCount(lateCount)
                .todayAbsentCount(absentCount)
                .recentEmployees(recentEmployees)
                .recentLeaves(recentLeaves)
                .employeesByDepartment(byDept)
                .build();
    }
}
