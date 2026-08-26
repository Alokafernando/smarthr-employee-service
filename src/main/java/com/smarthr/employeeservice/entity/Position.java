package com.smarthr.employeeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(length = 20)
    @Builder.Default
    private String status = "ACTIVE";
}
