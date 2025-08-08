package com.htetvehiclerental.htetvehiclerental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Payment;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Additional query methods can be defined here if needed

    @Query("SELECT r FROM Payment r WHERE r.customer.id = :customerId ")
    List<Payment> findByCustomer(@Param("customerId") Long customer_Id);    
}
