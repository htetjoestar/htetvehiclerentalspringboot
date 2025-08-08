package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.dto.EmployeeDto;
import com.htetvehiclerental.htetvehiclerental.dto.FetchCustomerRequest;
import com.htetvehiclerental.htetvehiclerental.dto.LoginRequestDto;
import com.htetvehiclerental.htetvehiclerental.dto.PasswordChangeRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Employee;
import com.htetvehiclerental.htetvehiclerental.service.EmployeeService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    // Get Employee REST API;
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployee();
        return ResponseEntity.ok(employees);
    }
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeID,
                                                    @RequestBody EmployeeDto updatedEmployee){
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeID, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }
    @PostMapping("/login")
    public ResponseEntity<EmployeeDto> login(@RequestBody LoginRequestDto request) {
        EmployeeDto userDto = employeeService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/portal-admin-login")
    public ResponseEntity<EmployeeDto> portalAdminLogin(@RequestBody LoginRequestDto request) {
        EmployeeDto userDto = employeeService.portalAdminLogin(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userDto);
    }  
    
    
    @PostMapping("/filter")
public ResponseEntity<Page<Employee>> searchVehicles(
    @RequestBody FetchCustomerRequest request,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String sort // e.g., sort=num_seats,desc
) {
    Pageable pageable;

    if (sort != null && !sort.isEmpty()) {
        String[] sortParams = sort.split(",");
        if (sortParams.length == 2) {
            String sortField = sortParams[0];
            String sortDirection = sortParams[1];
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        } else {
            pageable = PageRequest.of(page, size);
        }
    } else {
        pageable = PageRequest.of(page, size);
    }

    Page<Employee> vehicles = employeeService.getEmployeesByFilters(request.getKeyword(), request.getStartDate(),request.getEndDate(),request.getActive(),pageable);
    return ResponseEntity.ok(vehicles);
}
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        boolean success = employeeService.changePassword(request);

        if (success) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid employee ID or old password.");
        }
    }
}
