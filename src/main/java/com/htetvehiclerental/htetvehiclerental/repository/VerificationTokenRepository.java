package com.htetvehiclerental.htetvehiclerental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
    @Query("SELECT t FROM VerificationToken t WHERE (t.token = :token)")
    VerificationToken findByToken(@Param("token") String token);
}
