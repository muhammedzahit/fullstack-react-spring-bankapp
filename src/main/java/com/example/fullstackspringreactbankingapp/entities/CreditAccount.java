package com.example.fullstackspringreactbankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "credit_accounts")
public class CreditAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    private double accountLimit;
    private double minimumPaymentRate;
    private double interestRate;

    private boolean isMonthlyMinimumPayed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
