package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.EmployeeDto;
import com.smarthr.employeeservice.dto.EmployeeRequest;
import com.smarthr.employeeservice.entity.Department;
import com.smarthr.employeeservice.entity.Employee;
import com.smarthr.employeeservice.entity.Position;
import com.smarthr.employeeservice.exception.BadRequestException;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.DepartmentRepository;
import com.smarthr.employeeservice.repository.EmployeeRepository;
import com.smarthr.employeeservice.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeRequest request, String loggedInUser) {
        if (request.getEmail() != null && employeeRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Employee email already exists: " + request.getEmail());
        }

        String employeeNumber = request.getEmployeeNumber();
        if (employeeNumber == null || employeeNumber.isBlank()) {
            employeeNumber = "EMP-" + (1000 + employeeRepository.count() + 1);
        } else if (employeeRepository.existsByEmployeeNumber(employeeNumber)) {
            throw new BadRequestException("Employee number already exists: " + employeeNumber);
        }

        Employee employee = Employee.builder()
                .employeeNumber(employeeNumber)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .joinedDate(request.getJoinedDate())
                .departmentId(request.getDepartmentId())
                .positionId(request.getPositionId())
                .employmentType(request.getEmploymentType() != null ? request.getEmploymentType() : "FULL_TIME")
                .employmentStatus(request.getEmploymentStatus() != null ? request.getEmploymentStatus() : "ACTIVE")
                .emergencyContact(request.getEmergencyContact())
                .profileImageUrl(request.getProfileImageUrl())
                .build();

        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeRequest request, String loggedInUser) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (!employee.getEmail().equalsIgnoreCase(request.getEmail()) && employeeRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already taken: " + request.getEmail());
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setJoinedDate(request.getJoinedDate());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setPositionId(request.getPositionId());
        if (request.getEmploymentType() != null) employee.setEmploymentType(request.getEmploymentType());
        if (request.getEmploymentStatus() != null) employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setEmergencyContact(request.getEmergencyContact());
        employee.setProfileImageUrl(request.getProfileImageUrl());

        Employee updated = employeeRepository.save(employee);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id, String loggedInUser) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> searchEmployees(String keyword, Long departmentId, String status, Pageable pageable) {
        String cleanKeyword = (keyword != null && !keyword.isBlank()) ? keyword.trim() : null;
        String cleanStatus = (status != null && !status.isBlank()) ? status.trim() : null;
        if (cleanKeyword == null && departmentId == null && cleanStatus == null) {
            return employeeRepository.findAll(pageable).map(this::mapToDto);
        }
        return employeeRepository.searchEmployees(cleanKeyword, departmentId, cleanStatus, pageable).map(this::mapToDto);
    }

    private EmployeeDto mapToDto(Employee employee) {
        String deptName = null;
        if (employee.getDepartmentId() != null) {
            deptName = departmentRepository.findById(employee.getDepartmentId())
                    .map(Department::getName)
                    .orElse(null);
        }

        String posTitle = null;
        if (employee.getPositionId() != null) {
            posTitle = positionRepository.findById(employee.getPositionId())
                    .map(Position::getTitle)
                    .orElse(null);
        }

        return EmployeeDto.builder()
                .id(employee.getId())
                .employeeNumber(employee.getEmployeeNumber())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .address(employee.getAddress())
                .dateOfBirth(employee.getDateOfBirth())
                .joinedDate(employee.getJoinedDate())
                .departmentId(employee.getDepartmentId())
                .departmentName(deptName)
                .positionId(employee.getPositionId())
                .positionTitle(posTitle)
                .employmentType(employee.getEmploymentType())
                .employmentStatus(employee.getEmploymentStatus())
                .emergencyContact(employee.getEmergencyContact())
                .profileImageUrl(employee.getProfileImageUrl())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
