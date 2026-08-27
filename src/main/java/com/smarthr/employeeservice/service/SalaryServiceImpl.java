package com.smarthr.employeeservice.service;

import com.smarthr.employeeservice.dto.SalaryDto;
import com.smarthr.employeeservice.entity.Employee;
import com.smarthr.employeeservice.entity.Salary;
import com.smarthr.employeeservice.exception.ResourceNotFoundException;
import com.smarthr.employeeservice.repository.EmployeeRepository;
import com.smarthr.employeeservice.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaryDto> getAllSalaries(Integer month, Integer year) {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(emp -> getSalaryByEmployeeId(emp.getId())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryDto getSalaryByEmployeeId(Long employeeId) {
        Salary salary = salaryRepository.findTopByEmployeeIdOrderByEffectiveFromDesc(employeeId)
                .orElse(Salary.builder()
                        .employeeId(employeeId)
                        .basicSalary(BigDecimal.valueOf(150000.00))
                        .allowances(BigDecimal.valueOf(25000.00))
                        .deductions(BigDecimal.valueOf(12000.00))
                        .effectiveFrom(LocalDate.now().withDayOfMonth(1))
                        .build());
        return mapToDto(salary);
    }

    @Override
    @Transactional
    public SalaryDto saveSalary(SalaryDto dto, String loggedInUser) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));

        Salary salary = Salary.builder()
                .id(dto.getId())
                .employeeId(dto.getEmployeeId())
                .basicSalary(dto.getBasicSalary())
                .allowances(dto.getAllowances() != null ? dto.getAllowances() : BigDecimal.ZERO)
                .deductions(dto.getDeductions() != null ? dto.getDeductions() : BigDecimal.ZERO)
                .effectiveFrom(dto.getEffectiveFrom() != null ? dto.getEffectiveFrom() : LocalDate.now())
                .effectiveTo(dto.getEffectiveTo())
                .build();

        Salary saved = salaryRepository.save(salary);
        return mapToDto(saved);
    }

    private SalaryDto mapToDto(Salary salary) {
        String empName = null;
        if (salary.getEmployeeId() != null) {
            Employee emp = employeeRepository.findById(salary.getEmployeeId()).orElse(null);
            if (emp != null) {
                empName = emp.getFirstName() + " " + emp.getLastName();
            }
        }
        BigDecimal basic = salary.getBasicSalary() != null ? salary.getBasicSalary() : BigDecimal.ZERO;
        BigDecimal allow = salary.getAllowances() != null ? salary.getAllowances() : BigDecimal.ZERO;
        BigDecimal deduct = salary.getDeductions() != null ? salary.getDeductions() : BigDecimal.ZERO;
        BigDecimal net = basic.add(allow).subtract(deduct);

        return SalaryDto.builder()
                .id(salary.getId())
                .employeeId(salary.getEmployeeId())
                .employeeName(empName)
                .basicSalary(basic)
                .allowances(allow)
                .deductions(deduct)
                .netSalary(net)
                .effectiveFrom(salary.getEffectiveFrom())
                .effectiveTo(salary.getEffectiveTo())
                .build();
    }
}