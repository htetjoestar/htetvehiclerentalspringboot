package com.htetvehiclerental.htetvehiclerental.dto;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long payment_id;
    private Long customer;    
    private String card_number;
    private Date card_expiry_date;
    private String card_cvv;
    private String card_postal_code;
}
