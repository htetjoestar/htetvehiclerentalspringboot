package com.htetvehiclerental.htetvehiclerental.repository;

import com.htetvehiclerental.htetvehiclerental.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
@Query("SELECT p FROM PasswordResetToken p WHERE p.reset_token = :token")
Optional<PasswordResetToken> findByToken(@Param("token") String token);
}