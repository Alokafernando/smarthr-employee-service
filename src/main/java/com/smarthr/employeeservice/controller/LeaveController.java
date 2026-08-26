package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.CreateLeaveRequest;
import com.smarthr.employeeservice.dto.LeaveRequestDto;
import com.smarthr.employeeservice.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
@Tag(name = "Leave Management", description = "Endpoints for employee leave requests, approvals, and rejections")
public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping
    @Operation(summary = "Get all leave requests with optional status filter")
    public ResponseEntity<List<LeaveRequestDto>> getLeaves(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long employeeId) {

        if (employeeId != null) {
            return ResponseEntity.ok(leaveService.getLeavesByEmployee(employeeId));
        }
        return ResponseEntity.ok(leaveService.getAllLeaves(status));
    }

    @PostMapping
    @Operation(summary = "Submit a new leave request")
    public ResponseEntity<LeaveRequestDto> createLeave(
            @Valid @RequestBody CreateLeaveRequest request,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return new ResponseEntity<>(leaveService.createLeave(request, loggedInUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve a leave request")
    public ResponseEntity<LeaveRequestDto> approveLeave(
            @PathVariable Long id,
            @RequestHeader(value = "X-Auth-User", required = false) String approvedBy) {
        return ResponseEntity.ok(leaveService.approveLeave(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject a leave request")
    public ResponseEntity<LeaveRequestDto> rejectLeave(
            @PathVariable Long id,
            @RequestHeader(value = "X-Auth-User", required = false) String rejectedBy) {
        return ResponseEntity.ok(leaveService.rejectLeave(id, rejectedBy));
    }
}
