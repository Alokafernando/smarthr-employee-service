package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.AttendanceDto;
import com.smarthr.employeeservice.dto.CheckInRequest;
import com.smarthr.employeeservice.dto.CheckOutRequest;
import com.smarthr.employeeservice.entity.Attendance;
import com.smarthr.employeeservice.entity.Employee;
import com.smarthr.employeeservice.exception.BadRequestException;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.AttendanceRepository;
import com.smarthr.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAttendanceByDate(LocalDate date) {
        LocalDate searchDate = date != null ? date : LocalDate.now();
        return attendanceRepository.findByDate(searchDate).stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId).stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public AttendanceDto checkIn(CheckInRequest request, String loggedInUser) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();
        LocalTime checkInTime = request.getCheckIn() != null ? request.getCheckIn() : LocalTime.now();

        if (attendanceRepository.findByEmployeeIdAndDate(request.getEmployeeId(), date).isPresent()) {
            throw new BadRequestException("Employee already checked in for today: " + date);
        }

        String status = checkInTime.isAfter(LocalTime.of(9, 15)) ? "LATE" : "PRESENT";

        Attendance attendance = Attendance.builder()
                .employeeId(request.getEmployeeId())
                .date(date)
                .checkIn(checkInTime)
                .status(status)
                .build();

        Attendance saved = attendanceRepository.save(attendance);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public AttendanceDto checkOut(Long attendanceId, CheckOutRequest request, String loggedInUser) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with id: " + attendanceId));

        LocalTime checkOutTime = request != null && request.getCheckOut() != null ? request.getCheckOut() : LocalTime.now();
        attendance.setCheckOut(checkOutTime);

        Attendance updated = attendanceRepository.save(attendance);
        return mapToDto(updated);
    }

    private AttendanceDto mapToDto(Attendance attendance) {
        String empName = null;
        String empNumber = null;
        if (attendance.getEmployeeId() != null) {
            Employee emp = employeeRepository.findById(attendance.getEmployeeId()).orElse(null);
            if (emp != null) {
                empName = emp.getFirstName() + " " + emp.getLastName();
                empNumber = emp.getEmployeeNumber();
            }
        }
        return AttendanceDto.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployeeId())
                .employeeName(empName)
                .employeeNumber(empNumber)
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getStatus())
                .build();
    }
}
