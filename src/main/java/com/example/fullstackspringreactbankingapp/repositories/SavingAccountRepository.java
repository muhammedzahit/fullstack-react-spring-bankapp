package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Integer> {
    void deleteSavingAccountById(Long accountId);
    boolean existsSavingAccountById(Long accountId);

    Optional<SavingAccount> getSavingAccountById(Long accountId);

    Optional<List<SavingAccount>> getSavingAccountsByIsInterestEnabledIsTrue();
}
