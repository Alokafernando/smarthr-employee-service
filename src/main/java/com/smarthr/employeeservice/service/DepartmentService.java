package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.DepartmentDto;
import com.smarthr.employeeservice.entity.Department;
import com.smarthr.employeeservice.exception.BadRequestException;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.DepartmentRepository;
import com.smarthr.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto createDepartment(DepartmentDto request);
    DepartmentDto updateDepartment(Long id, DepartmentDto request);
    void deleteDepartment(Long id);
}
