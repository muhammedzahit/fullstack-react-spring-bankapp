package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditAccountRepository extends JpaRepository<CreditAccount, Integer> {
    void deleteCreditAccountById(Long accountId);
    boolean existsCreditAccountById(Long accountId);

    Optional<CreditAccount> getCreditAccountById(Long accountId);

    Optional<List<CreditAccount>> getCreditAccountsByBalanceIsLessThan(Double lessThan);
}
