package com.smarthr.employeeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // Annual, Casual, Medical, Maternity

    @Column(length = 255)
    private String description;

    @Column(name = "annual_allocation", nullable = false)
    @Builder.Default
    private Integer annualAllocation = 14;
}
