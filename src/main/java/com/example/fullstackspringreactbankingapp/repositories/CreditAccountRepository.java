package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditAccountRepository extends JpaRepository<CreditAccount, Integer> {
}
