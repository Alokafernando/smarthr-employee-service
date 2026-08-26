package com.smarthr.employeeservice.controller;

import com.smarthr.employeeservice.dto.EmployeeDto;
import com.smarthr.employeeservice.dto.EmployeeRequest;
import com.smarthr.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "Endpoints for employee CRUD and search")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Get all employees with pagination")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return ResponseEntity.ok(employeeService.getAllEmployees(PageRequest.of(page, size, sort)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee details by ID")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeRequest request,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return new ResponseEntity<>(employeeService.createEmployee(request, loggedInUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee details by ID")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request, loggedInUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee by ID")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id,
            @RequestHeader(value = "X-Auth-User", required = false) String loggedInUser) {
        employeeService.deleteEmployee(id, loggedInUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search employees by keyword, department, status")
    public ResponseEntity<Page<EmployeeDto>> searchEmployees(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return ResponseEntity.ok(employeeService.searchEmployees(keyword, departmentId, status, PageRequest.of(page, size, sort)));
    }
}
