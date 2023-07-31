package com.example.fullstackspringreactbankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "credit_accounts")
public class CreditAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    private double accountLimit;
    private double minimumPaymentRate;
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
