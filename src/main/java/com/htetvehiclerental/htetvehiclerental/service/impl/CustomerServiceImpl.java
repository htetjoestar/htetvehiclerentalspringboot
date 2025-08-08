package com.htetvehiclerental.htetvehiclerental.service.impl;

import com.htetvehiclerental.htetvehiclerental.service.CustomerService;
import com.htetvehiclerental.htetvehiclerental.service.EmailService;
import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.PasswordResetToken;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.entity.VerificationToken;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.CustomerMapper;
import com.htetvehiclerental.htetvehiclerental.mapper.ReservationMapper;
import com.htetvehiclerental.htetvehiclerental.repository.CustomerRepository;
import com.htetvehiclerental.htetvehiclerental.repository.PasswordResetTokenRepository;
import com.htetvehiclerental.htetvehiclerental.repository.VerificationTokenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private EmailService emailService;
    private VerificationTokenRepository tokenRepo;

    private PasswordResetTokenRepository passwordTokenRepo;

    // Implement the methods from CustomerService interface here
    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedCustomer);
        verificationToken.setTokenExpiryDate(LocalDateTime.now().plusDays(1));
       VerificationToken savedToken = tokenRepo.save(verificationToken);
        
        emailService.sendVerificationEmail(savedCustomer, savedToken.getToken());

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        
        existingCustomer.setCust_first_name(updatedCustomer.getCust_first_name());
        existingCustomer.setCust_last_name(updatedCustomer.getCust_last_name());
        existingCustomer.setCust_email(updatedCustomer.getCust_email());
        existingCustomer.setCust_phone_number(updatedCustomer.getCust_phone_number());
        existingCustomer.setCust_license_number(updatedCustomer.getCust_license_number());
        existingCustomer.setCust_date_of_birth(updatedCustomer.getCust_date_of_birth());
        existingCustomer.setCust_license_expiry_date(updatedCustomer.getCust_license_expiry_date());
        existingCustomer.setCust_emergency_contact_name(updatedCustomer.getCust_emergency_contact_name());
        existingCustomer.setCust_emergency_contact_number(updatedCustomer.getCust_emergency_contact_number());
        existingCustomer.setCust_password(updatedCustomer.getCust_password());
        existingCustomer.setCust_id_doc(updatedCustomer.getCust_id_doc());
        existingCustomer.setCust_last_action(updatedCustomer.getCust_last_action());
        existingCustomer.setCust_active(updatedCustomer.isCust_active());
        existingCustomer.setCust_created_date(updatedCustomer.getCust_created_date());
        existingCustomer.setCust_modified_date(updatedCustomer.getCust_modified_date());
        existingCustomer.setCust_id_img(updatedCustomer.getCust_id_img());
        
        Customer savedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }
    @Override
    public CustomerDto login(String username, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(username, password);
    if (customer == null) {
        throw new RuntimeException("Invalid email or password");
    }
    if (!customer.isEnabled()) {
        throw new RuntimeException("Email not verified");
    }        
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public boolean verifyToken(String token) {
        VerificationToken verificationToken = tokenRepo.findByToken(token);
        if (verificationToken == null || verificationToken.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Customer user = verificationToken.getUser();
        user.setEnabled(true);
        customerRepository.save(user);
        return true;
    }
    @Override
    public void requestPasswordReset(String email, String lastName) {
        Customer customer = customerRepository.findByEmailAndLastName(email, lastName)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setCustomer(customer);
        resetToken.setReset_token(token);
        resetToken.setReset_expiryDate(LocalDateTime.now().plusHours(1)); // 1 hour expiry
        passwordTokenRepo.save(resetToken);

        emailService.sendPasswordResetEmail(customer, token);
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordTokenRepo.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getReset_expiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        Customer customer = resetToken.getCustomer();
        customer.setCust_password(newPassword); // you should encode this!
        customerRepository.save(customer);

        passwordTokenRepo.delete(resetToken);
        return true;
    }
    @Override
    public void updateIdentificationImage(Long id, String imgUrl) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

    customer.setCust_id_img(imgUrl);
    customerRepository.save(customer);
    }

    
    @Override
    public Page<Customer> getCustomersByFilters(String keyword, LocalDate StartDate, LocalDate EndDate, Boolean active, Pageable pageable) {
            Page<Customer> customers = customerRepository.searchWithFilters(keyword,StartDate,EndDate, active,pageable);
            return customers;
    }  
}
