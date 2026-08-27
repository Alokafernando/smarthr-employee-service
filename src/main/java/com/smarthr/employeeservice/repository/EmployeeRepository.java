package com.smarthr.employeeservice.repository;

import com.smarthr.employeeservice.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeNumber(String employeeNumber);
    Optional<Employee> findByEmail(String email);
    boolean existsByEmployeeNumber(String employeeNumber);
    boolean existsByEmail(String email);
    long countByEmploymentStatus(String status);
    long countByDepartmentId(Long departmentId);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')) " +
            "OR LOWER(e.employeeNumber) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%'))) " +
            "AND (:departmentId IS NULL OR e.departmentId = :departmentId) " +
            "AND (:status IS NULL OR :status = '' OR e.employmentStatus = :status)")
    Page<Employee> searchEmployees(@Param("keyword") String keyword,
                                  @Param("departmentId") Long departmentId,
                                  @Param("status") String status,
                                  Pageable pageable);
}