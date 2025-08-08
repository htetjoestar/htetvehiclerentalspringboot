package com.htetvehiclerental.htetvehiclerental.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>{
    @Query(value ="SELECT m FROM Maintenance m WHERE (:status is NULL or m.maint_status = :status)",
           countQuery = "SELECT COUNT(m) FROM Maintenance m WHERE (m.maint_status = :status)")
    Page<Maintenance> findByStatus(@Param("status") String status, Pageable pageable);
}
