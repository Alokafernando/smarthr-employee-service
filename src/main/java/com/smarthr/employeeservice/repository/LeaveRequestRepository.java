package com.smarthr.employeeservice.repository;

import com.smarthr.employeeservice.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
    List<LeaveRequest> findByStatusOrderByCreatedAtDesc(String status);
    long countByStatus(String status);
}
