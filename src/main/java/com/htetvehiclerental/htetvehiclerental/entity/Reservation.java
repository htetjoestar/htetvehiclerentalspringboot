package com.htetvehiclerental.htetvehiclerental.entity;
import java.time.LocalDate;

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
@Table(name = "reservation")
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = true)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", nullable = true)
    private Vehicle vehicle;
    @Column(name = "pick_up_date")
    private LocalDate pick_up_date;  
    @Column(name = "drop_off_date")
    private LocalDate drop_off_date;  
    @Column(name = "actual_pick_up_date")
    private LocalDate actual_pick_up_date;  
    @Column(name = "actual_drop_off_date")
    private LocalDate actual_drop_off_date;
    @Column(name = "baby_seat")
    private int baby_seat;
    @Column(name = "insurance")
    private int insurance;
    @Column(name = "damages")
    private int damages;
    @Column(name = "late_fee")
    private int late_fee;
    @Column(name = "res_status")
    private String res_status; 
    @Column(name = "total_charge")
    private int total_charge;
    @Column(name = "res_created_date")
    private LocalDateTime res_created_date;
    @Column(name = "res_modified_date")
    private LocalDateTime res_modified_date; 
    @Column(name = "res_last_action")
    private String res_last_action; 
    @Column(name = "res_cancellation_date")
    private LocalDateTime cancellation_date;    
}
