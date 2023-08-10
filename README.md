# Section 3 : Implementing Business Layer

![alt text](/drawio/fullstack.png)

In section 2 we have created the database and the tables. Now we will create the business layer. The business layer will be a logical functions that will be called by the API layer. The business layer will be responsible for the following:

1. Connecting to the database
2. Executing the SQL queries
3. Returning the results to the API layer

In this section we created `services` folder for logical classes. Since we dont have any presentation layer yet, we will test the business layer using CommandLineRunner bean in Spring Boot. Classes inherited from CommandLineRunner will be executed after the application is started.

```java
// packages

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

```

For seeing the effects of the functions, you can use debugger or comment the functions that you dont want to see the effects.

I think most cruicial part of the business layer is the `fulfillMonthlyOperations` function. This function will be called by the director at the end of the month. This function will be responsible for the following:

1. Paying the interests of the saving accounts
2. Debiting the interests of the credit accounts
3. Send Warning users that have not payed their credit minimum payment. In real life this users warned by legal procedures.

```java
@Transactional
    public void fulfillMonthlyOperations(){
        // Pay interest for saving accounts whose enabled saving
        Optional<List<SavingAccount>> savingAccountsByInterestEnabledIsTrue = savingAccountRepository.getSavingAccountsByIsInterestEnabledIsTrue();
        savingAccountsByInterestEnabledIsTrue.ifPresent(savingAccounts -> savingAccounts.stream().toList().forEach(
                savingAccount -> savingAccount.setBalance(savingAccount.getBalance() * (1 + savingAccount.getInterestRate()))
        ));

        // Debit Credit Cart Owners that uses their limits
        Optional<List<CreditAccount>> creditAccounts = creditAccountRepository.getCreditAccountsByBalanceIsLessThan(0.0);
        creditAccounts.ifPresent(creditAccounts_ ->
            creditAccounts_.stream().toList().forEach(
                    creditAccount -> creditAccount.setBalance(creditAccount.getBalance() * (1 + creditAccount.getInterestRate()))
            )
        );

    }
```