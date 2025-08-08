
package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.dto.PaymentDto;
import com.htetvehiclerental.htetvehiclerental.entity.Payment;

public class PaymentMapper {

    public static Payment mapToPayment(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setPayment_id(paymentDto.getPayment_id());
        payment.setCard_number(paymentDto.getCard_number());
        payment.setCard_expiry_date(paymentDto.getCard_expiry_date());
        payment.setCard_cvv(paymentDto.getCard_cvv());
        payment.setCard_postal_code(paymentDto.getCard_postal_code());
        return payment;
    }

    public static PaymentDto mapToPaymentDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment_id(payment.getPayment_id());
        paymentDto.setCustomer(payment.getCustomer().getCustomer_id());
        paymentDto.setCard_number(payment.getCard_number());
        paymentDto.setCard_expiry_date(payment.getCard_expiry_date());
        paymentDto.setCard_postal_code(payment.getCard_postal_code());
        return paymentDto;
    }
}