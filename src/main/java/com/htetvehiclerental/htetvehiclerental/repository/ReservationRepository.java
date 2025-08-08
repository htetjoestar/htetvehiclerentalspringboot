package com.htetvehiclerental.htetvehiclerental.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.dto.ReportDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationsPerDayDto;
import com.htetvehiclerental.htetvehiclerental.dto.YearMonthDto;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId")
    List<Reservation> findByCustomerId(@Param("customerId") Long customer_Id);

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId "+
            "AND (r.res_status = :status)")
    List<Reservation> findByCustomerStatus(@Param("customerId") Long customer_Id,
                                           @Param("status") String status);

    @Query(value = "SELECT r FROM Reservation r WHERE r.customer.id = :customerId "+
            "AND (r.res_status = :status)",
            countQuery = "SELECT Count(r) FROM Reservation r WHERE r.customer.id = :customerId "+
            "AND (r.res_status = :status)")
    Page<Reservation> findByCustomerStatusPageable(@Param("customerId") Long customer_Id,
                                           @Param("status") String status,
                                           Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r " +
        "WHERE (r.pick_up_date > :now AND r.pick_up_date < :nextweek)" +
        "OR (r.drop_off_date > :now AND r.drop_off_date < :nextweek)",
        countQuery = "SELECT Count(r) FROM Reservation r " +
        "WHERE (r.pick_up_date > :now AND r.pick_up_date < :nextweek)" +
        "OR (r.drop_off_date > :now AND r.drop_off_date < :nextweek)")
    Page<Reservation> findNextWeek(
            @Param("now") LocalDate now,
            @Param("nextweek") LocalDate nextweek,
              Pageable pageable
        );
        
       @Query(value = "SELECT r FROM Reservation r " +
              "WHERE (:status IS NULL OR r.res_status = :status) " +
              "AND (:nextweek IS NULL OR " +
              "      ((r.pick_up_date > :now AND r.pick_up_date < :nextweek) " +
              "       OR (r.drop_off_date > :now AND r.drop_off_date < :nextweek)))",
              countQuery = "SELECT COUNT(r) FROM Reservation r " +
              "WHERE (:status IS NULL OR r.res_status = :status) " +
              "AND (:nextweek IS NULL OR " +
              "      ((r.pick_up_date > :now AND r.pick_up_date < :nextweek) " +
              "       OR (r.drop_off_date > :now AND r.drop_off_date < :nextweek)))")
       Page<Reservation> findByFilters(
              @Param("status") String status,
              @Param("now") LocalDate now,
              @Param("nextweek") LocalDate nextweek,
              Pageable pageable
       );
    @Query("SELECT FUNCTION('DATE_FORMAT', r.actual_pick_up_date, '%Y-%m') AS month, SUM(r.total_charge) " +
           "FROM Reservation r " +
           "WHERE r.actual_pick_up_date IS NOT NULL AND r.actual_drop_off_date IS NOT NULL " +
           "GROUP BY month")
    List<Object[]> getTotalChargePerMonth();

    @Query("SELECT FUNCTION('DATE_FORMAT', r.actual_pick_up_date, '%Y-%m') AS month, COUNT(r) " +
           "FROM Reservation r " +
           "WHERE r.actual_pick_up_date IS NOT NULL AND r.actual_drop_off_date IS NOT NULL " +
           "GROUP BY month")
    List<Object[]> getReservationCountPerMonth();

    @Query("SELECT FUNCTION('DATE_FORMAT', r.actual_pick_up_date, '%Y-%m') AS month, " +
           "SUM(DATEDIFF(r.actual_drop_off_date, r.actual_pick_up_date)) " +
           "FROM Reservation r " +
           "WHERE r.actual_pick_up_date IS NOT NULL AND r.actual_drop_off_date IS NOT NULL " +
           "GROUP BY month")
    List<Object[]> getRentalDaysPerMonth();

    @Query("SELECT FUNCTION('DATE_FORMAT', r.actual_pick_up_date, '%Y-%m') AS month, COUNT(DISTINCT r.customer.customer_id) " +
           "FROM Reservation r " +
           "WHERE r.actual_pick_up_date IS NOT NULL " +
           "GROUP BY month")
    List<Object[]> getUniqueCustomersPerMonth();

    @Query("SELECT FUNCTION('DATE_FORMAT', r.actual_pick_up_date, '%Y-%m') AS month, COUNT(DISTINCT r.vehicle.vehicle_id) " +
           "FROM Reservation r " +
           "WHERE r.actual_pick_up_date IS NOT NULL " +
           "GROUP BY month")
    List<Object[]> getUniqueVehiclesPerMonth();

    @Query("SELECT r FROM Reservation r WHERE r.actual_pick_up_date BETWEEN :start AND :end AND r.actual_drop_off_date IS NOT NULL")
    List<Reservation> findByActualPickUpDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
       @Query("SELECT new com.htetvehiclerental.htetvehiclerental.dto.ReservationsPerDayDto(DATE(r.res_created_date), COUNT(r)) " +
              "FROM Reservation r " +
              "WHERE r.res_created_date BETWEEN :start AND :end " +
              "GROUP BY CAST(r.res_created_date AS date) " +
              "ORDER BY CAST(r.res_created_date AS date)")
       List<ReservationsPerDayDto> countNewReservationsPerDayBetween(
       @Param("start") LocalDateTime start,
       @Param("end") LocalDateTime end
       );


    @Query("SELECT new com.htetvehiclerental.htetvehiclerental.dto.YearMonthDto(FUNCTION('YEAR', r.pick_up_date), FUNCTION('MONTH', r.pick_up_date)) " +
           "FROM Reservation r WHERE r.pick_up_date IS NOT NULL " +
           "GROUP BY FUNCTION('YEAR', r.pick_up_date), FUNCTION('MONTH', r.pick_up_date) " +
           "ORDER BY FUNCTION('YEAR', r.pick_up_date), FUNCTION('MONTH', r.pick_up_date)")
    List<YearMonthDto> findDistinctReservationMonths();
       
       @Query("SELECT r FROM Reservation r " +
              "WHERE r.customer.customer_id = :customerId " +
              "AND ((r.pick_up_date = :today AND r.actual_pick_up_date IS NULL) " +
              "     OR (r.drop_off_date = :today AND r.actual_drop_off_date IS NULL))")
       List<Reservation> findReservationsForToday(@Param("customerId") Long customerId,
                                                 @Param("today") LocalDate today);
}
