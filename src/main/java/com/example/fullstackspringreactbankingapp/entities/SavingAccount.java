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
@Table(name = "saving_accounts")
public class SavingAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    private boolean isInterestEnabled;
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
