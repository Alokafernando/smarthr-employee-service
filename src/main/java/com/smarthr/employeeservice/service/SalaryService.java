package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.SalaryDto;
import java.util.List;

public interface SalaryService {
    SalaryDto getSalaryByEmployeeId(Long employeeId);
    SalaryDto saveSalary(SalaryDto salaryDto, String loggedInUser);
    List<SalaryDto> getAllSalaries(Integer month, Integer year);
}