package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.PositionDto;
import com.smarthr.employeeservice.entity.Department;
import com.smarthr.employeeservice.entity.Position;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.DepartmentRepository;
import com.smarthr.employeeservice.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PositionService {
    List<PositionDto> getAllPositions();
    PositionDto getPositionById(Long id);
    PositionDto createPosition(PositionDto request);
    PositionDto updatePosition(Long id, PositionDto request);
    void deletePosition(Long id);
}
