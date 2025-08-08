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
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;
    @Column(name = "emp_first_name")
    private String emp_first_name;
    @Column(name = "emp_last_name")
    private String emp_last_name;
    @Column(name = "emp_email")
    private String emp_email;        
    @Column(name = "emp_password")
    private String emp_password; 
    @Column(name = "emp_role")
    private String emp_role;   
    @Column(name = "emp_phone_number")
    private String emp_phone_number;
    @Column(name = "emp_emergency_contact_name")
    private String emp_emergency_contact_name;
    @Column(name = "emp_emergency_contact_number")
    private String emp_emergency_contact_number;         
    @Column(name = "emp_active")
    private boolean emp_active;
    @Column(name = "emp_created_date")
    private LocalDateTime emp_created_date;
    @Column(name = "emp_modified_date")
    private LocalDateTime emp_modified_date; 
    @Column(name = "emp_last_action")
    private String emp_last_action; 
    @Column(name = "emp_deactivated_date")
    private LocalDateTime emp_deactivated_date;         
}
