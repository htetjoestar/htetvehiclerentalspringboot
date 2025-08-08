package com.htetvehiclerental.htetvehiclerental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.htetvehiclerental.htetvehiclerental.dto.PaymentDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Payment;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.PaymentMapper;
import com.htetvehiclerental.htetvehiclerental.mapper.ReservationMapper;
import com.htetvehiclerental.htetvehiclerental.repository.CustomerRepository;
import com.htetvehiclerental.htetvehiclerental.repository.PaymentRepository;
import com.htetvehiclerental.htetvehiclerental.service.PaymentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private CustomerRepository customerRepository;    
    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapToPayment(paymentDto);
        Customer customer = customerRepository.findById(paymentDto.getCustomer()).orElseThrow(() ->
                new ResourceNotFoundException("Customer does not exist with given id: " + paymentDto.getCustomer()));
        payment.setCustomer(customer);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.mapToPaymentDto(savedPayment);
    }
    @Override
    public PaymentDto getPaymentbyId(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        return PaymentMapper.mapToPaymentDto(payment);
    }
    @Override
    public List<PaymentDto> getPaymentsByCustomerId(Long customerId) {
        List<Payment> payments = paymentRepository.findByCustomer(customerId);
        return payments.stream()
                .map((payment) -> PaymentMapper.mapToPaymentDto(payment))
                .collect(Collectors.toList());
    }
    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        paymentRepository.deleteById(id);
    }
}
