package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.SalaryDto;

public interface SalaryService {
    SalaryDto getSalaryByEmployeeId(Long employeeId);
    SalaryDto saveSalary(SalaryDto salaryDto, String loggedInUser);
}
