package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.AttendanceDto;
import com.smarthr.employeeservice.dto.CheckInRequest;
import com.smarthr.employeeservice.dto.CheckOutRequest;
import com.smarthr.employeeservice.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Management", description = "Endpoints for employee daily check-in and check-out")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @Operation(summary = "Get attendance by date or employee")
    public ResponseEntity<List<AttendanceDto>> getAttendance(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long employeeId) {

        if (employeeId != null) {
            return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
        }
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date != null ? date : LocalDate.now()));
    }

    @PostMapping("/check-in")
    @Operation(summary = "Check in an employee for today")
    public ResponseEntity<AttendanceDto> checkIn(
            @Valid @RequestBody CheckInRequest request,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return new ResponseEntity<>(attendanceService.checkIn(request, loggedInUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/check-out")
    @Operation(summary = "Check out an attendance record")
    public ResponseEntity<AttendanceDto> checkOut(
            @PathVariable Long id,
            @RequestBody(required = false) CheckOutRequest request,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return ResponseEntity.ok(attendanceService.checkOut(id, request, loggedInUser));
    }
}
