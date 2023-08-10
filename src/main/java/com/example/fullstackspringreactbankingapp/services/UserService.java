package com.example.fullstackspringreactbankingapp.services;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.enums.AccountType;
import com.example.fullstackspringreactbankingapp.repositories.CreditAccountRepository;
import com.example.fullstackspringreactbankingapp.repositories.SavingAccountRepository;
import com.example.fullstackspringreactbankingapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SavingAccountRepository savingAccountRepository;
    private final CreditAccountRepository creditAccountRepository;

    public void checkUserId(Long userId) throws Exception {
        if (!userRepository.existsUserById(userId)) {
            throw new Exception("User ID Not Found !!!");
        }
    }

    public Object checkAccountId(Long accountId, Long userId, AccountType accountType) throws Exception{
        checkUserId(userId);

        if(accountType == AccountType.SAVING){
            if(!savingAccountRepository.existsSavingAccountById(accountId))
                throw new Exception("Account ID Not Found !!!");
            Optional<SavingAccount> optionalSavingAccount = savingAccountRepository.getSavingAccountById(accountId);
            Hibernate.initialize(optionalSavingAccount.get().getUser());
            if(optionalSavingAccount.get().getUser().getId() != userId)
                throw new Exception("This Account Not Belong to this User ID !!!");
            return optionalSavingAccount.get();
        }
        else if(accountType == AccountType.CREDIT){
            if(!creditAccountRepository.existsCreditAccountById(accountId))
                throw new Exception("Account ID Not Found !!!");
            Optional<CreditAccount> optional = creditAccountRepository.getCreditAccountById(accountId);
            Hibernate.initialize(optional.get().getUser());
            if(optional.get().getUser().getId() != userId)
                throw new Exception("This Account Not Belong to this User ID !!!");
            return optional.get();
        }
        return null;
    }

    public void checkSavingEnabled(long accountId) throws Exception {
        if(savingAccountRepository.getSavingAccountById(accountId).get().isInterestEnabled())
            throw new Exception("You cannot deposit money because your account enabled to saving. Please try deposit money end of this month !!!");
    }


    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public void changeBalanceAtAccount(double change, long userId,long accountId, AccountType accountType) throws Exception {
        Object account = checkAccountId(accountId, userId, accountType);

        if (accountType == AccountType.CREDIT){
            CreditAccount creditAccount = (CreditAccount) account;
            if(creditAccount.getBalance() + change < -1 * creditAccount.getAccountLimit())
                throw new Exception("You cannot withdraw money more than your account limit !!!");
            creditAccount.setBalance(creditAccount.getBalance() + change);
        }
        else if(accountType == AccountType.SAVING){
            checkSavingEnabled(accountId);

            SavingAccount savingAccount = (SavingAccount) account;
            if(savingAccount.getBalance() + change < 0)
                throw new Exception("You cannot withdraw money more than your balance !!!, You can create Credit Account for this purpose");
            savingAccount.setBalance(savingAccount.getBalance() + change);
        }
    }

    @Transactional
    public void depositMoneyToAccount(double money, long userId,long accountId, AccountType accountType) throws Exception {
        changeBalanceAtAccount(money, userId, accountId, accountType);
    }

    @Transactional
    public void withdrawMoneyFromAccount(double money, long userId,long accountId, AccountType accountType) throws Exception {
        changeBalanceAtAccount(money * -1, userId, accountId, accountType);
    }

    @Transactional
    public void enableSaving(long userId, long accountId) throws Exception {
        Object object = checkAccountId(accountId, userId, AccountType.SAVING);
        SavingAccount savingAccount = (SavingAccount) object;
        savingAccount.setInterestEnabled(true);

        savingAccountRepository.save(savingAccount);
    }

    @Transactional
    public void payMinimumRateOfCredit(double money, long userId, long accountId) throws Exception {
        Object account = checkAccountId(accountId, userId, AccountType.CREDIT);
        CreditAccount creditAccount = (CreditAccount) account;

        if(creditAccount.isMonthlyMinimumPayed())
            throw new Exception("You already paid the minimum rate");

        if(creditAccount.getBalance() > 0){
            throw new Exception("You do not have debit !!!");
        }

        // calculate minimum rate
        double minimum = creditAccount.getBalance() * creditAccount.getMinimumPaymentRate();
        if(money < minimum)
            throw new Exception("Money should be equal or greater than minimum rate !!!");
        else if(money > minimum)
            creditAccount.setBalance(creditAccount.getBalance() + (money - minimum));

        creditAccount.setMonthlyMinimumPayed(true);
    }

}
