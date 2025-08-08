package com.htetvehiclerental.htetvehiclerental.entity;
import java.time.LocalDate;

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
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @Column(name = "maintenance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenance_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", nullable = true)
    private Vehicle vehicle;
    @Column(name = "maint_status")
    private String maint_status;
    @Column(name = "details")
    private String details;
    @Column(name = "start_date")
    private LocalDate start_date;  
    @Column(name = "end_date")
    private LocalDate end_date;  
}
