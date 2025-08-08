package com.htetvehiclerental.htetvehiclerental.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id @GeneratedValue
    @Column(name = "token_id")
    private Long token_id;
    @Column(name = "token")
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = true)
    private Customer user;
    @Column(name = "tokenExpiryDate")
    private LocalDateTime tokenExpiryDate;
    
}
