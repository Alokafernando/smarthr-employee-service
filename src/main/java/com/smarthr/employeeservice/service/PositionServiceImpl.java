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

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PositionDto> getAllPositions() {
        return positionRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PositionDto getPositionById(Long id) {
        Position pos = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        return mapToDto(pos);
    }

    @Override
    @Transactional
    public PositionDto createPosition(PositionDto request) {
        Position pos = Position.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .departmentId(request.getDepartmentId())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .build();
        return mapToDto(positionRepository.save(pos));
    }

    @Override
    @Transactional
    public PositionDto updatePosition(Long id, PositionDto request) {
        Position pos = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        pos.setTitle(request.getTitle());
        pos.setDescription(request.getDescription());
        pos.setDepartmentId(request.getDepartmentId());
        if (request.getStatus() != null) pos.setStatus(request.getStatus());
        return mapToDto(positionRepository.save(pos));
    }

    @Override
    @Transactional
    public void deletePosition(Long id) {
        Position pos = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        positionRepository.delete(pos);
    }

    private PositionDto mapToDto(Position pos) {
        String deptName = null;
        if (pos.getDepartmentId() != null) {
            deptName = departmentRepository.findById(pos.getDepartmentId())
                    .map(Department::getName)
                    .orElse(null);
        }
        return PositionDto.builder()
                .id(pos.getId())
                .title(pos.getTitle())
                .description(pos.getDescription())
                .departmentId(pos.getDepartmentId())
                .departmentName(deptName)
                .status(pos.getStatus())
                .build();
    }
}
