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
public class PositionDto {
    private Long id;

    @NotBlank(message = "Position title is required")
    private String title;

    private String description;
    private Long departmentId;
    private String departmentName;
    private String status;
}
