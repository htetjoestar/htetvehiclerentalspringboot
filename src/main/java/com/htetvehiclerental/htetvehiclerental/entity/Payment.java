package com.htetvehiclerental.htetvehiclerental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = true)
    private Customer customer;    

    @Column(name = "card_number")
    private String card_number;

    @Column(name = "card_expiry_date")
    private Date card_expiry_date;

    @Column(name = "card_cvv")
    private String card_cvv;
    
    @Column(name = "card_postal_code")
    private String card_postal_code;
}
