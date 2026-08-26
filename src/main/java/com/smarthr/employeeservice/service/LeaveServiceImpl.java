package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.CreateLeaveRequest;
import com.smarthr.employeeservice.dto.LeaveRequestDto;
import com.smarthr.employeeservice.entity.Employee;
import com.smarthr.employeeservice.entity.LeaveRequest;
import com.smarthr.employeeservice.entity.LeaveType;
import com.smarthr.employeeservice.exception.BadRequestException;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.EmployeeRepository;
import com.smarthr.employeeservice.repository.LeaveRequestRepository;
import com.smarthr.employeeservice.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getAllLeaves(String status) {
        if (status != null && !status.isBlank()) {
            return leaveRequestRepository.findByStatusOrderByCreatedAtDesc(status.toUpperCase())
                    .stream().map(this::mapToDto).toList();
        }
        return leaveRequestRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getLeavesByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId)
                .stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public LeaveRequestDto createLeave(CreateLeaveRequest request, String loggedInUser) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave Type not found with id: " + request.getLeaveTypeId()));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("Leave end date cannot be before start date");
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employeeId(request.getEmployeeId())
                .leaveTypeId(request.getLeaveTypeId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status("PENDING")
                .build();

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public LeaveRequestDto approveLeave(Long leaveId, String approvedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

        leaveRequest.setStatus("APPROVED");
        leaveRequest.setApprovedBy(approvedBy != null ? approvedBy : "HR_MANAGER");

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public LeaveRequestDto rejectLeave(Long leaveId, String rejectedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

        leaveRequest.setStatus("REJECTED");
        leaveRequest.setApprovedBy(rejectedBy != null ? rejectedBy : "HR_MANAGER");

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    private LeaveRequestDto mapToDto(LeaveRequest leaveRequest) {
        String empName = null;
        String empNum = null;
        if (leaveRequest.getEmployeeId() != null) {
            Employee emp = employeeRepository.findById(leaveRequest.getEmployeeId()).orElse(null);
            if (emp != null) {
                empName = emp.getFirstName() + " " + emp.getLastName();
                empNum = emp.getEmployeeNumber();
            }
        }

        String typeName = null;
        if (leaveRequest.getLeaveTypeId() != null) {
            typeName = leaveTypeRepository.findById(leaveRequest.getLeaveTypeId())
                    .map(LeaveType::getName)
                    .orElse(null);
        }

        return LeaveRequestDto.builder()
                .id(leaveRequest.getId())
                .employeeId(leaveRequest.getEmployeeId())
                .employeeName(empName)
                .employeeNumber(empNum)
                .leaveTypeId(leaveRequest.getLeaveTypeId())
                .leaveTypeName(typeName)
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .reason(leaveRequest.getReason())
                .status(leaveRequest.getStatus())
                .approvedBy(leaveRequest.getApprovedBy())
                .createdAt(leaveRequest.getCreatedAt())
                .build();
    }
}
