package com.example.fullstackspringreactbankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    @OneToOne
    @JoinColumn(name = "token_user_id")
    private TokenUser tokenUser;

    private Double netSalary;


    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private Employee responsible;

    @OneToMany(mappedBy = "user")
    private List<SavingAccount> savingAccounts;

    @OneToMany(mappedBy = "user")
    private List<CreditAccount> creditAccounts;

}
