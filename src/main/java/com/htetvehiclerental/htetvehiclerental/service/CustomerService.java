package com.htetvehiclerental.htetvehiclerental.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;

public interface CustomerService {
    CustomerDto getCustomerById(Long id);
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer);
    CustomerDto login(String username, String password);
    boolean verifyToken(String token);
    void requestPasswordReset(String email, String lastName);
    boolean resetPassword(String token, String newPassword);
    void updateIdentificationImage(Long id, String imgUrl);
    Page<Customer> getCustomersByFilters(String keyword, LocalDate StartDate, LocalDate EndDate, Boolean active, Pageable pageable);
}
