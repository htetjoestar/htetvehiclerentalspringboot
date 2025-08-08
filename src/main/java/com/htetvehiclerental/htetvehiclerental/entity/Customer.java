package com.htetvehiclerental.htetvehiclerental.entity;
import java.time.LocalDateTime;
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
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    @Column(name = "cust_email")
    private String cust_email;
    @Column(name = "cust_first_name")
    private String cust_first_name;
    @Column(name = "cust_last_name")
    private String cust_last_name;
    @Column(name = "cust_id_doc")
    private String cust_id_doc;
    @Column(name = "cust_id_img")
    private String cust_id_img;         
    @Column(name = "cust_password")
    private String cust_password;
    @Column(name = "cust_date_of_birth")
    private LocalDate cust_date_of_birth;   
    @Column(name = "cust_phone_number")
    private String cust_phone_number;
    @Column(name = "cust_emergency_contact_name")
    private String cust_emergency_contact_name;
    @Column(name = "cust_emergency_contact_number")
    private String cust_emergency_contact_number;   
    @Column(name = "cust_license_number")
    private String cust_license_number;
    @Column(name = "cust_license_expiry_date")
    private LocalDate cust_license_expiry_date;
    @Column(name = "cust_active")
    private boolean cust_active;
    @Column(name = "cust_created_date")
    private LocalDateTime cust_created_date;
    @Column(name = "cust_modified_date")
    private LocalDateTime cust_modified_date;
    @Column(name = "cust_last_action")
    private String cust_last_action; 
    @Column(name = "cust_deactivated_date")
    private LocalDateTime cust_deactivated_date;        
    @Column(name = "enabled")
    private boolean enabled = false;
 
}
