package com.htetvehiclerental.htetvehiclerental.service;

import com.htetvehiclerental.htetvehiclerental.dto.EmployeeDto;
import com.htetvehiclerental.htetvehiclerental.dto.PasswordChangeRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee();
    EmployeeDto updateEmployee(Long vehicleId, EmployeeDto updatedVehicle);
    EmployeeDto login(String username, String password);
    EmployeeDto portalAdminLogin(String username, String password);
    Page<Employee> getEmployeesByFilters(String keyword, LocalDate StartDate, LocalDate EndDate, Boolean active, Pageable pageable);
    boolean changePassword(PasswordChangeRequest request);
}
