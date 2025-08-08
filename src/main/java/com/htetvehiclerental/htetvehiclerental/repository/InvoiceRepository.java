package com.htetvehiclerental.htetvehiclerental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Employee;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
    @Query("SELECT i FROM Invoice i WHERE i.reservation.reservation_id = :reservation_id")
    Invoice findByReservationId(@Param("reservation_id") Long reservation_id);

    @Query("SELECT i FROM Invoice i WHERE i.paid = :paid AND i.reservation.customer.customer_id = :customer_id ")
    List<Invoice> findByCustomerId(@Param("customer_id") Long customer_id,
                                    @Param("paid") boolean paid);

@Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
       "FROM Invoice i WHERE i.paid = false AND i.reservation.customer.customer_id = :customerId")
boolean existsUnpaidByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT i FROM Invoice i " +
           "WHERE i.reservation.customer.customer_id = :customerId " +
           "AND i.paid = false")
    List<Invoice> findUnpaidInvoicesByCustomerId(@Param("customerId") Long customerId);
}
