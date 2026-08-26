package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.PositionDto;
import com.smarthr.employeeservice.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@Tag(name = "Position Management", description = "Endpoints for managing job roles and positions")
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    @Operation(summary = "Get all positions")
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get position by ID")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable Long id) {
        return ResponseEntity.ok(positionService.getPositionById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new position")
    public ResponseEntity<PositionDto> createPosition(@Valid @RequestBody PositionDto request) {
        return new ResponseEntity<>(positionService.createPosition(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update position by ID")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable Long id, @Valid @RequestBody PositionDto request) {
        return ResponseEntity.ok(positionService.updatePosition(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete position by ID")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
