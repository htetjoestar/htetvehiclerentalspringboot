package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.EmployeeDto;
import com.htetvehiclerental.htetvehiclerental.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployee_id(employee.getEmployee_id());
        employeeDto.setEmp_email(employee.getEmp_email());
        employeeDto.setEmp_password(employee.getEmp_password());
        employeeDto.setEmp_first_name(employee.getEmp_first_name());
        employeeDto.setEmp_last_name(employee.getEmp_last_name());
        employeeDto.setEmp_phone_number(employee.getEmp_phone_number());
        employeeDto.setEmp_role(employee.getEmp_role());
        employeeDto.setEmp_emergency_contact_name(employee.getEmp_emergency_contact_name());
        employeeDto.setEmp_emergency_contact_number(employee.getEmp_emergency_contact_number());
        employeeDto.setEmp_deactivated_date(employee.getEmp_deactivated_date());
        employeeDto.setEmp_created_date(employee.getEmp_created_date());
        employeeDto.setEmp_modified_date(employee.getEmp_modified_date());
        employeeDto.setEmp_last_action(employee.getEmp_last_action());
        employeeDto.setEmp_active(employee.isEmp_active());
        return employeeDto;
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setEmployee_id(employeeDto.getEmployee_id());
        employee.setEmp_email(employeeDto.getEmp_email());
        employee.setEmp_password(employeeDto.getEmp_password());    
        employee.setEmp_first_name(employeeDto.getEmp_first_name());
        employee.setEmp_last_name(employeeDto.getEmp_last_name());
        employee.setEmp_phone_number(employeeDto.getEmp_phone_number());
        employee.setEmp_role(employeeDto.getEmp_role());
        employee.setEmp_emergency_contact_name(employeeDto.getEmp_emergency_contact_name());
        employee.setEmp_emergency_contact_number(employeeDto.getEmp_emergency_contact_number());
        employee.setEmp_deactivated_date(employeeDto.getEmp_deactivated_date());
        employee.setEmp_created_date(employeeDto.getEmp_created_date());
        employee.setEmp_modified_date(employeeDto.getEmp_modified_date());
        employee.setEmp_last_action(employeeDto.getEmp_last_action());
        employee.setEmp_active(employeeDto.isEmp_active());
        
        return employee;
    }
}