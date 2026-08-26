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

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return mapToDto(dept);
    }

    @Override
    @Transactional
    public DepartmentDto createDepartment(DepartmentDto request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new BadRequestException("Department with name '" + request.getName() + "' already exists");
        }
        Department dept = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .build();
        return mapToDto(departmentRepository.save(dept));
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentDto request) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        if (request.getStatus() != null) dept.setStatus(request.getStatus());
        return mapToDto(departmentRepository.save(dept));
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(dept);
    }

    private DepartmentDto mapToDto(Department dept) {
        long count = employeeRepository.countByDepartmentId(dept.getId());
        return DepartmentDto.builder()
                .id(dept.getId())
                .name(dept.getName())
                .description(dept.getDescription())
                .status(dept.getStatus())
                .employeeCount(count)
                .build();
    }
}
