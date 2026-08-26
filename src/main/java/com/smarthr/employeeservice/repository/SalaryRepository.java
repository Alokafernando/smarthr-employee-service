package com.smarthr.employeeservice.repository;

import com.smarthr.employeeservice.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findTopByEmployeeIdOrderByEffectiveFromDesc(Long employeeId);
    List<Salary> findByEmployeeIdOrderByEffectiveFromDesc(Long employeeId);
}
