package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.EmployeeDto;
import com.smarthr.employeeservice.dto.EmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<EmployeeDto> getAllEmployees(Pageable pageable);
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createEmployee(EmployeeRequest request, String loggedInUser);
    EmployeeDto updateEmployee(Long id, EmployeeRequest request, String loggedInUser);
    void deleteEmployee(Long id, String loggedInUser);
    Page<EmployeeDto> searchEmployees(String keyword, Long departmentId, String status, Pageable pageable);
}
