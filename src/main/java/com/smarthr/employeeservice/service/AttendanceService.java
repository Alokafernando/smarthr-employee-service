package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.AttendanceDto;
import com.smarthr.employeeservice.dto.CheckInRequest;
import com.smarthr.employeeservice.dto.CheckOutRequest;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<AttendanceDto> getAttendanceByDate(LocalDate date);
    List<AttendanceDto> getAttendanceByEmployee(Long employeeId);
    AttendanceDto checkIn(CheckInRequest request, String loggedInUser);
    AttendanceDto checkOut(Long attendanceId, CheckOutRequest request, String loggedInUser);
}
