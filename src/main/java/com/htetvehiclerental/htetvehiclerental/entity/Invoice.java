package com.htetvehiclerental.htetvehiclerental.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoice_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id", nullable = true)
    private Reservation reservation;
    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", nullable = true)
    private Vehicle vehicle;
    @Column(name = "damage_description")
    private String damageDescription;
    @Column(name = "invoice_created_date")
    private LocalDateTime invoice_created_date;
    @Column(name = "paid")
    private boolean paid = false;
}
