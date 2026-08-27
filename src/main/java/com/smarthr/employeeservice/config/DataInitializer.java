package com.smarthr.employeeservice.config;

import com.smarthr.employeeservice.entity.*;
import com.smarthr.employeeservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public void run(String... args) {
        if (employeeRepository.count() == 0) {
            log.info("Seeding initial SmartHR enterprise departments, positions, employees, salaries, and attendance...");

            // 1. Departments (Find or create)
            Department itDept = departmentRepository.findByName("Engineering")
                    .orElseGet(() -> departmentRepository.save(Department.builder()
                            .name("Engineering")
                            .description("Software Development and Cloud Infrastructure")
                            .build()));

            Department hrDept = departmentRepository.findByName("Human Resources")
                    .orElseGet(() -> departmentRepository.save(Department.builder()
                            .name("Human Resources")
                            .description("Talent Acquisition and People Operations")
                            .build()));

            Department finDept = departmentRepository.findByName("Finance")
                    .orElseGet(() -> departmentRepository.save(Department.builder()
                            .name("Finance")
                            .description("Financial Operations and Payroll Management")
                            .build()));

            Department mktDept = departmentRepository.findByName("Marketing")
                    .orElseGet(() -> departmentRepository.save(Department.builder()
                            .name("Marketing")
                            .description("Brand, Growth and Corporate Communications")
                            .build()));

            // 2. Positions
            Position p1 = positionRepository.findByDepartmentId(itDept.getId()).stream()
                    .filter(p -> p.getTitle().equals("Cloud Software Engineer")).findFirst()
                    .orElseGet(() -> positionRepository.save(Position.builder().title("Cloud Software Engineer").description("Cloud & Backend Systems").departmentId(itDept.getId()).build()));

            Position p2 = positionRepository.findByDepartmentId(itDept.getId()).stream()
                    .filter(p -> p.getTitle().equals("Senior Backend Architect")).findFirst()
                    .orElseGet(() -> positionRepository.save(Position.builder().title("Senior Backend Architect").description("Distributed Microservices Architecture").departmentId(itDept.getId()).build()));

            Position p3 = positionRepository.findByDepartmentId(hrDept.getId()).stream()
                    .filter(p -> p.getTitle().equals("HR Director")).findFirst()
                    .orElseGet(() -> positionRepository.save(Position.builder().title("HR Director").description("HR Leadership & Policy").departmentId(hrDept.getId()).build()));

            Position p4 = positionRepository.findByDepartmentId(finDept.getId()).stream()
                    .filter(p -> p.getTitle().equals("Payroll Specialist")).findFirst()
                    .orElseGet(() -> positionRepository.save(Position.builder().title("Payroll Specialist").description("Payroll & Benefits").departmentId(finDept.getId()).build()));

            Position p5 = positionRepository.findByDepartmentId(mktDept.getId()).stream()
                    .filter(p -> p.getTitle().equals("Marketing Lead")).findFirst()
                    .orElseGet(() -> positionRepository.save(Position.builder().title("Marketing Lead").description("Growth & Branding").departmentId(mktDept.getId()).build()));

            // 3. Leave Types
            LeaveType annual = leaveTypeRepository.findByName("Annual Leave")
                    .orElseGet(() -> leaveTypeRepository.save(LeaveType.builder().name("Annual Leave").description("Standard annual paid time off").annualAllocation(14).build()));
            LeaveType casual = leaveTypeRepository.findByName("Casual Leave")
                    .orElseGet(() -> leaveTypeRepository.save(LeaveType.builder().name("Casual Leave").description("Short notice urgent personal leave").annualAllocation(7).build()));
            LeaveType medical = leaveTypeRepository.findByName("Medical Leave")
                    .orElseGet(() -> leaveTypeRepository.save(LeaveType.builder().name("Medical Leave").description("Sick and medical appointments").annualAllocation(14).build()));

            // 4. Employees
            Employee emp1 = employeeRepository.save(Employee.builder()
                    .employeeNumber("EMP-1001")
                    .firstName("Buddhika")
                    .lastName("Fernando")
                    .email("employee@smarthr.com")
                    .phone("+94 77 123 4567")
                    .address("123 Galle Road, Colombo 03, Sri Lanka")
                    .dateOfBirth(LocalDate.of(1995, 6, 15))
                    .joinedDate(LocalDate.of(2023, 1, 10))
                    .departmentId(itDept.getId())
                    .positionId(p1.getId())
                    .employmentType("FULL_TIME")
                    .employmentStatus("ACTIVE")
                    .emergencyContact("Kavinda Fernando (+94 71 987 6543)")
                    .build());

            Employee emp2 = employeeRepository.save(Employee.builder()
                    .employeeNumber("EMP-1002")
                    .firstName("Chamari")
                    .lastName("Perera")
                    .email("hrmanager@smarthr.com")
                    .phone("+94 77 234 5678")
                    .address("45 Kandy Road, Kiribathgoda, Sri Lanka")
                    .dateOfBirth(LocalDate.of(1990, 3, 22))
                    .joinedDate(LocalDate.of(2022, 5, 1))
                    .departmentId(hrDept.getId())
                    .positionId(p3.getId())
                    .employmentType("FULL_TIME")
                    .employmentStatus("ACTIVE")
                    .emergencyContact("Sunil Perera (+94 70 111 2233)")
                    .build());

            Employee emp3 = employeeRepository.save(Employee.builder()
                    .employeeNumber("EMP-1003")
                    .firstName("System")
                    .lastName("Admin")
                    .email("admin@smarthr.com")
                    .phone("+94 77 345 6789")
                    .address("78 High Level Road, Nugegoda, Sri Lanka")
                    .dateOfBirth(LocalDate.of(1988, 11, 5))
                    .joinedDate(LocalDate.of(2021, 3, 15))
                    .departmentId(itDept.getId())
                    .positionId(p2.getId())
                    .employmentType("FULL_TIME")
                    .employmentStatus("ACTIVE")
                    .emergencyContact("Admin Contact (+94 77 000 9999)")
                    .build());

            Employee emp4 = employeeRepository.save(Employee.builder()
                    .employeeNumber("EMP-1004")
                    .firstName("Kasun")
                    .lastName("Silva")
                    .email("hrofficer@smarthr.com")
                    .phone("+94 77 456 7890")
                    .address("12 Temple Road, Negombo, Sri Lanka")
                    .dateOfBirth(LocalDate.of(1993, 8, 19))
                    .joinedDate(LocalDate.of(2023, 7, 1))
                    .departmentId(finDept.getId())
                    .positionId(p4.getId())
                    .employmentType("FULL_TIME")
                    .employmentStatus("ACTIVE")
                    .emergencyContact("Anura Silva (+94 72 333 4455)")
                    .build());

            Employee emp5 = employeeRepository.save(Employee.builder()
                    .employeeNumber("EMP-1005")
                    .firstName("Nuwan")
                    .lastName("Fernando")
                    .email("manager@smarthr.com")
                    .phone("+94 77 567 8901")
                    .address("89 Duplication Road, Colombo 04, Sri Lanka")
                    .dateOfBirth(LocalDate.of(1992, 1, 30))
                    .joinedDate(LocalDate.of(2022, 11, 15))
                    .departmentId(mktDept.getId())
                    .positionId(p5.getId())
                    .employmentType("FULL_TIME")
                    .employmentStatus("ACTIVE")
                    .emergencyContact("Malkanthi Fernando (+94 76 555 6677)")
                    .build());

            // 5. Salaries
            salaryRepository.save(Salary.builder().employeeId(emp1.getId()).basicSalary(BigDecimal.valueOf(180000.00)).allowances(BigDecimal.valueOf(25000.00)).deductions(BigDecimal.valueOf(12000.00)).effectiveFrom(LocalDate.of(2023, 1, 1)).build());
            salaryRepository.save(Salary.builder().employeeId(emp2.getId()).basicSalary(BigDecimal.valueOf(220000.00)).allowances(BigDecimal.valueOf(35000.00)).deductions(BigDecimal.valueOf(18000.00)).effectiveFrom(LocalDate.of(2022, 5, 1)).build());
            salaryRepository.save(Salary.builder().employeeId(emp3.getId()).basicSalary(BigDecimal.valueOf(300000.00)).allowances(BigDecimal.valueOf(50000.00)).deductions(BigDecimal.valueOf(25000.00)).effectiveFrom(LocalDate.of(2021, 3, 1)).build());
            salaryRepository.save(Salary.builder().employeeId(emp4.getId()).basicSalary(BigDecimal.valueOf(140000.00)).allowances(BigDecimal.valueOf(20000.00)).deductions(BigDecimal.valueOf(10000.00)).effectiveFrom(LocalDate.of(2023, 7, 1)).build());
            salaryRepository.save(Salary.builder().employeeId(emp5.getId()).basicSalary(BigDecimal.valueOf(200000.00)).allowances(BigDecimal.valueOf(30000.00)).deductions(BigDecimal.valueOf(15000.00)).effectiveFrom(LocalDate.of(2022, 11, 1)).build());

            // 6. Attendance for Today
            LocalDate today = LocalDate.now();
            attendanceRepository.save(Attendance.builder().employeeId(emp1.getId()).date(today).checkIn(LocalTime.of(8, 45)).checkOut(LocalTime.of(17, 15)).status("PRESENT").build());
            attendanceRepository.save(Attendance.builder().employeeId(emp2.getId()).date(today).checkIn(LocalTime.of(9, 5)).status("PRESENT").build());
            attendanceRepository.save(Attendance.builder().employeeId(emp3.getId()).date(today).checkIn(LocalTime.of(8, 30)).checkOut(LocalTime.of(17, 0)).status("PRESENT").build());
            attendanceRepository.save(Attendance.builder().employeeId(emp4.getId()).date(today).checkIn(LocalTime.of(9, 30)).status("LATE").build());

            // 7. Sample Leave Requests
            leaveRequestRepository.save(LeaveRequest.builder().employeeId(emp1.getId()).leaveTypeId(annual.getId()).startDate(today.plusDays(5)).endDate(today.plusDays(7)).reason("Family vacation trip").status("APPROVED").approvedBy("Chamari Perera").build());
            leaveRequestRepository.save(LeaveRequest.builder().employeeId(emp4.getId()).leaveTypeId(medical.getId()).startDate(today.plusDays(2)).endDate(today.plusDays(3)).reason("Routine medical checkup").status("PENDING").build());

            log.info("SmartHR enterprise data initialized successfully with 5 employees, salaries, and attendance records!");
        }
    }
}
