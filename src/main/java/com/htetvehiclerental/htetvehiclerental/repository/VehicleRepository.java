package com.htetvehiclerental.htetvehiclerental.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

    
        @Query("SELECT v FROM Vehicle v WHERE v.vehicle_status = 'Listed' " +
           "AND (:color IS NULL OR v.color = :color) " +
           "AND (:num_seats IS NULL OR v.num_seats = :num_seats) " +
           "AND (:type IS NULL OR v.type = :type)")
       List<Vehicle> findByFilters(@Param("color") String color,
                                @Param("num_seats") Integer num_seats,
                                @Param("type") String type);
 
        @Query(
            value = "SELECT v FROM Vehicle v WHERE " +
                    "(:keyword IS NULL OR LOWER(v.model) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(v.license_plate) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                    "AND (:vehicle_status IS NULL OR v.vehicle_status = :vehicle_status) " +
                    "AND (:num_seats IS NULL OR v.num_seats = :num_seats) " +
                    "AND (:type IS NULL OR v.type = :type)",

            countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE " +
                        "(:keyword IS NULL OR LOWER(v.model) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(v.license_plate) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                        "AND (:vehicle_status IS NULL OR v.vehicle_status = :vehicle_status) " +
                        "AND (:num_seats IS NULL OR v.num_seats = :num_seats) " +
                        "AND (:type IS NULL OR v.type = :type)"
        )
        Page<Vehicle> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("vehicle_status") String vehicle_status,
            @Param("num_seats") Integer num_seats,
            @Param("type") String type,
            Pageable pageable
        );

       @Query("SELECT v FROM Vehicle v WHERE (:status = 'ALL' or v.vehicle_status = :status)")
       List<Vehicle> findByStatus(@Param("status") String status);

@Query("SELECT v FROM Vehicle v " +
       "WHERE v.vehicle_status = 'Listed' " +
       "AND (:color IS NULL OR v.color = :color) " +
       "AND (:num_seats IS NULL OR v.num_seats = :num_seats) " +
       "AND (:types IS NULL OR v.type IN :types) " +
       "AND NOT EXISTS (" +
       "    SELECT r FROM Reservation r " +
       "    WHERE r.vehicle = v " +
       "    AND r.pick_up_date < :event_end_date " +
       "    AND r.drop_off_date > :event_start_date " +
       "    AND (r.res_status = 'Pending' OR r.res_status = 'Rented' OR r.res_status = 'Reserved')" +
       ")")
List<Vehicle> findAvailableFilteredVehicles(
        @Param("color") String color,
        @Param("num_seats") Integer num_seats,
        @Param("types") List<String> types, // changed from String to List<String>
        @Param("event_start_date") LocalDate event_start_date,
        @Param("event_end_date") LocalDate event_end_date
);

    @Query("""
        SELECT v FROM Vehicle v
        WHERE v.vehicle_status = 'Listed' AND v.vehicle_id NOT IN (
            SELECT r.vehicle.vehicle_id FROM Reservation r
            WHERE r.pick_up_date > CURRENT_DATE OR r.drop_off_date > CURRENT_DATE
        ) AND (:type IS NULL OR v.type = :type)
    """)
    List<Vehicle> findAvailableVehiclesWithoutFutureReservations(@Param("type") String type);

    @Query(value = "SELECT * FROM vehicle ORDER BY RAND() LIMIT 10", nativeQuery = true)
List<Vehicle> findTenRandomVehicles();
}
