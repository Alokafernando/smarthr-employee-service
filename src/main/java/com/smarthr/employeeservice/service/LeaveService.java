package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.CreateLeaveRequest;
import com.smarthr.employeeservice.dto.LeaveRequestDto;

import java.util.List;

public interface LeaveService {
    List<LeaveRequestDto> getAllLeaves(String status);
    List<LeaveRequestDto> getLeavesByEmployee(Long employeeId);
    LeaveRequestDto createLeave(CreateLeaveRequest request, String loggedInUser);
    LeaveRequestDto approveLeave(Long leaveId, String approvedBy);
    LeaveRequestDto rejectLeave(Long leaveId, String rejectedBy);
}
