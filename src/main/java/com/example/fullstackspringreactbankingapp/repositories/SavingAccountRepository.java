package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Integer> {
}
