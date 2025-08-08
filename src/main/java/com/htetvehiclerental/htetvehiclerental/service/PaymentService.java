package com.htetvehiclerental.htetvehiclerental.service;

import java.io.IOException;
import java.util.List;


import com.htetvehiclerental.htetvehiclerental.dto.PaymentDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;

public interface PaymentService {
    
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto getPaymentbyId(Long paymentId);
    List<PaymentDto> getPaymentsByCustomerId(Long customerId);
    void deletePayment(Long paymentId) throws ResourceNotFoundException;
}
