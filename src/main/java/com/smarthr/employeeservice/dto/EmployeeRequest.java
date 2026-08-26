package com.smarthr.employeeservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private String employeeNumber;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate joinedDate;
    private Long departmentId;
    private Long positionId;
    private String employmentType; // FULL_TIME, PART_TIME, CONTRACT, INTERN
    private String employmentStatus; // ACTIVE, ON_LEAVE, TERMINATED
    private String emergencyContact;
    private String profileImageUrl;
}
