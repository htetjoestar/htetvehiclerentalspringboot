package com.htetvehiclerental.htetvehiclerental.service.impl;

import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.dto.EmployeeDto;
import com.htetvehiclerental.htetvehiclerental.dto.PasswordChangeRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Employee;
import com.htetvehiclerental.htetvehiclerental.mapper.CustomerMapper;
import com.htetvehiclerental.htetvehiclerental.mapper.EmployeeMapper;
import com.htetvehiclerental.htetvehiclerental.repository.EmployeeRepository;
import com.htetvehiclerental.htetvehiclerental.service.EmployeeService;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService
{
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }
    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        
        existingEmployee.setEmp_first_name(updatedEmployee.getEmp_first_name());
        existingEmployee.setEmp_last_name(updatedEmployee.getEmp_last_name());
        existingEmployee.setEmp_email(updatedEmployee.getEmp_email());
        existingEmployee.setEmp_role(updatedEmployee.getEmp_role());
        existingEmployee.setEmp_phone_number(updatedEmployee.getEmp_phone_number());       
        existingEmployee.setEmp_password(updatedEmployee.getEmp_password());
        existingEmployee.setEmp_created_date(updatedEmployee.getEmp_created_date());
        existingEmployee.setEmp_modified_date(updatedEmployee.getEmp_modified_date());
        existingEmployee.setEmp_last_action(updatedEmployee.getEmp_last_action());
        existingEmployee.setEmp_emergency_contact_name(updatedEmployee.getEmp_emergency_contact_name());
        existingEmployee.setEmp_emergency_contact_number(updatedEmployee.getEmp_emergency_contact_number());
        existingEmployee.setEmp_active(updatedEmployee.isEmp_active());
        existingEmployee.setEmp_deactivated_date(updatedEmployee.getEmp_deactivated_date());
        
        
        Employee savedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }
    @Override
    public EmployeeDto login(String username, String password) {
        Employee employee = employeeRepository.findByEmailAndPassword(username, password);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto portalAdminLogin(String username, String password) {
        Employee employee = employeeRepository.findPortalAdmin(username, password);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

        
@Override
public Page<Employee> getEmployeesByFilters(String keyword, LocalDate startDate, LocalDate endDate, Boolean active, Pageable pageable) {
    Page<Employee> employees = employeeRepository.searchWithFilters(keyword, startDate, endDate, active,pageable);
    return employees;
}

    @Override
    public boolean changePassword(PasswordChangeRequest request) {
        Optional<Employee> employeeOpt = employeeRepository.findEmployeeById(request.getEmployeeId());

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            if (employee.getEmp_password().equals(request.getOldPassword())) {
                employee.setEmp_password(request.getNewPassword());
                employeeRepository.save(employee);
                return true;
            }
        }

        return false;
    }
}