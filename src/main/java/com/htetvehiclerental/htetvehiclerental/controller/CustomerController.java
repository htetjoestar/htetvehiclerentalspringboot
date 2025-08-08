package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.dto.FetchCustomerRequest;
import com.htetvehiclerental.htetvehiclerental.service.CustomerService;
import com.htetvehiclerental.htetvehiclerental.dto.LoginRequestDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationFilters;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleFilterRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;
    private static final String IMAGE_UPLOAD_DIR = "uploads/identification/";

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getEmployeeById(@PathVariable("id") Long customerID){
        CustomerDto customerDto = customerService.getCustomerById(customerID);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createEmployee(@RequestBody CustomerDto cutomerDto){
        CustomerDto savedEmployee = customerService.createCustomer(cutomerDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long customerID,
                                                    @RequestBody CustomerDto updatedEmployee){
        CustomerDto customerDto = customerService.updateCustomer(customerID, updatedEmployee);
        return ResponseEntity.ok(customerDto);
    }

@PostMapping("/filter")
public ResponseEntity<Page<Customer>> searchVehicles(
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

    Page<Customer> vehicles = customerService.getCustomersByFilters(request.getKeyword(), request.getStartDate(),request.getEndDate(),request.getActive(),pageable);
    return ResponseEntity.ok(vehicles);
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
    try {
        CustomerDto userDto = customerService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userDto);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyCustomer(@RequestParam("token") String token) {
        boolean isVerified = customerService.verifyToken(token);
    if (isVerified) {
        return ResponseEntity.ok("Email verified successfully.");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired verification token.");
    }
    }  
    
    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email, @RequestParam String lastName) {
        try {
            customerService.requestPasswordReset(email, lastName);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            customerService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id,
                                              @RequestParam("image") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, file.getBytes());

            String imgUrl = "/identification/" + fileName; // This should match your static resource mapping
            customerService.updateIdentificationImage(id, imgUrl);

            return ResponseEntity.ok(imgUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }
}
