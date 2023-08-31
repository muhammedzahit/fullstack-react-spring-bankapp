package com.example.fullstackspringreactbankingapp.services;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.enums.AccountType;
import com.example.fullstackspringreactbankingapp.exceptions.EmployeeServiceException;
import com.example.fullstackspringreactbankingapp.exceptions.UserServiceException;
import com.example.fullstackspringreactbankingapp.repositories.CreditAccountRepository;
import com.example.fullstackspringreactbankingapp.repositories.SavingAccountRepository;
import com.example.fullstackspringreactbankingapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final UserRepository userRepository;
    private final SavingAccountRepository savingAccountRepository;
    private final CreditAccountRepository creditAccountRepository;

    public void checkUserId(Long userId) throws Exception {
        if (!userRepository.existsUserById(userId)) {
            throw new Exception(UserServiceException.UserIdNotFound.getValue());
        }

    }

    @Transactional
    public Long createAccount(Long userId, AccountType accountType) throws Exception {
        checkUserId(userId);
        Optional<User> optionalUser = userRepository.getUserById(userId);
        User user = optionalUser.get();

        if(accountType == AccountType.CREDIT){
            if(user.getCreditAccounts() == null) user.setCreditAccounts(new ArrayList<>());
            CreditAccount newCreditAccount = CreditAccount.builder().user(user)
                    .accountLimit(user.getNetSalary()*2).balance(0.0).interestRate(0.31).minimumPaymentRate(0.3).build();
            user.getCreditAccounts().add(newCreditAccount);
            creditAccountRepository.save(newCreditAccount);
            return newCreditAccount.getId();
        }
        else if(accountType == AccountType.SAVING){
            if(user.getSavingAccounts() == null) user.setSavingAccounts(new ArrayList<>());
            SavingAccount newSavingAccount = SavingAccount.builder().user(user)
                    .balance(0.0).interestRate(0.31).isInterestEnabled(false).build();
            user.getSavingAccounts().add(newSavingAccount);
            savingAccountRepository.save(newSavingAccount);
            return newSavingAccount.getId();
        }
        return -1L;
    }

    @Transactional
    public void deleteAccount(Long accountId, AccountType accountType) throws Exception {
        if(accountType == AccountType.CREDIT){
            if(!creditAccountRepository.existsCreditAccountById(accountId))
                throw new Exception(EmployeeServiceException.NoCreditAccountFound.getValue());
            creditAccountRepository.deleteCreditAccountById(accountId);
        }
        else if(accountType == AccountType.SAVING){
            if(!savingAccountRepository.existsSavingAccountById(accountId))
                throw new Exception(EmployeeServiceException.NoSavingAccountFound.getValue());
            savingAccountRepository.deleteSavingAccountById(accountId);
        }
    }

    @Transactional
    public List<SavingAccount> getSavingAccountsByUserId(Long userId) throws Exception {
        checkUserId(userId);
        Optional<User> optionalUser = userRepository.getUserById(userId);
        Hibernate.initialize(optionalUser.get().getSavingAccounts());
        List<SavingAccount> savingAccounts = optionalUser.get().getSavingAccounts();
        return savingAccounts;
    }

}
