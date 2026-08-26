package com.smarthr.employeeservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long id;

    @NotBlank(message = "Department name is required")
    private String name;

    private String description;
    private String status;
    private Long employeeCount;
}
