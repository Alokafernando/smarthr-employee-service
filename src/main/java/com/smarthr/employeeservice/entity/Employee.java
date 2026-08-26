package com.smarthr.employeeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_number", nullable = false, unique = true, length = 30)
    private String employeeNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "employment_type", length = 30)
    @Builder.Default
    private String employmentType = "FULL_TIME"; // FULL_TIME, PART_TIME, CONTRACT, INTERN

    @Column(name = "employment_status", length = 30)
    @Builder.Default
    private String employmentStatus = "ACTIVE"; // ACTIVE, ON_LEAVE, TERMINATED, RESIGNED

    @Column(name = "emergency_contact", length = 100)
    private String emergencyContact;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
