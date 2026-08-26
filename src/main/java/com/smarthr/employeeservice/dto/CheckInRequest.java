package com.smarthr.employeeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    private LocalDate date;
    private LocalTime checkIn;
}
