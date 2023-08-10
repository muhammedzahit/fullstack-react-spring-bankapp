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
