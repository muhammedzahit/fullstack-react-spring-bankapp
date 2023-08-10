package com.example.fullstackspringreactbankingapp;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.enums.AccountType;
import com.example.fullstackspringreactbankingapp.repositories.UserRepository;
import com.example.fullstackspringreactbankingapp.services.DirectorService;
import com.example.fullstackspringreactbankingapp.services.EmployeeService;
import com.example.fullstackspringreactbankingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final DirectorService directorService;
    private final EmployeeService employeeService;
    private final UserService userService;
    @Override
    public void run(String... args) throws Exception {

        // Enable Debugger to better see effects of service functions
        // Or you can use comment method

        // Testing directorService Functions

        Employee newRecruit = Employee.builder().director(null).users(null).email(null).fullName("mm")
                .password("mm").netSalary(12000.0).build();
        directorService.addNewRecruit(newRecruit);

        directorService.giveRaise(newRecruit.getId(), 0.25);

        User newUser = User.builder().fullName("muhammed zahid").password("1234").email("mzahit@gmail.com").netSalary(25000.0).build();

        userService.addUser(newUser);

        //directorService.dismissRecruit(newRecruit.getId());

        employeeService.createAccount(newUser.getId(), AccountType.SAVING);

        // Testing employeeService functions

        List<SavingAccount> savingAccounts = employeeService.getSavingAccountsByUserId(newUser.getId());
        SavingAccount savingAccount = savingAccounts.get(0);
        System.out.println(savingAccount.getBalance() + " // " + savingAccount.getInterestRate() + " // " + savingAccount.getId());

        //employeeService.deleteAccount(savingAccount.getId(), AccountType.SAVING);

        //savingAccounts = employeeService.getSavingAccountsByUserId(newUser.getId());
        //System.out.println(savingAccounts.size());

        // Testing userService functions
        // Saving Account Scenario

        userService.depositMoneyToAccount(10000.0, newUser.getId(), savingAccount.getId(), AccountType.SAVING);

        userService.withdrawMoneyFromAccount(5000.0, newUser.getId(), savingAccount.getId(), AccountType.SAVING);

        userService.enableSaving(newUser.getId(), savingAccount.getId());

        // Catch Error
        // Because saving enabled on this account it cannot let service withdraw money
        try {
            userService.withdrawMoneyFromAccount(1.0, newUser.getId(), savingAccount.getId(), AccountType.SAVING);
        }catch (Exception ex){
            System.out.println("ERROR : " + ex.getMessage());
        }

        // Credit Account Scenario

        long creditAccountId = employeeService.createAccount(newUser.getId(), AccountType.CREDIT);

        userService.withdrawMoneyFromAccount(10000.00, newUser.getId(), creditAccountId, AccountType.CREDIT);

        // Pay or Debit interests end of month
        // Saving Account interest is 0.31 so saving account balance should be 5000*1.31 = 6550
        // Credit account interest also is 0.31 so credit account balance should be -10000*1.31 = -13.100
        directorService.fulfillMonthlyOperations();
    }

}
