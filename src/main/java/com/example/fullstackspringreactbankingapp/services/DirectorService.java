package com.example.fullstackspringreactbankingapp.services;

import com.example.fullstackspringreactbankingapp.entities.CreditAccount;
import com.example.fullstackspringreactbankingapp.entities.Director;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.entities.SavingAccount;
import com.example.fullstackspringreactbankingapp.repositories.CreditAccountRepository;
import com.example.fullstackspringreactbankingapp.repositories.DirectorRepository;
import com.example.fullstackspringreactbankingapp.repositories.EmployeeRepository;
import com.example.fullstackspringreactbankingapp.repositories.SavingAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {
    final private EmployeeRepository employeeRepository;
    final private SavingAccountRepository savingAccountRepository;
    final private CreditAccountRepository creditAccountRepository;
    final private DirectorRepository directorRepository;

    private boolean checkEmployeeIdExists(Long employeeId) throws Exception {
        if(employeeRepository.existsEmployeeById(employeeId)){
            return true;
        }
        else {
            throw new Exception("Employee ID not found");
        }
    }

    public void addNewRecruit(Employee recruit) throws Exception {
        if(recruit.getNetSalary() < 0){
            throw new Exception("Net Salary must be positive !!!");
        }
        employeeRepository.save(recruit);
    }

    public void addNewRecruitWithRandomDirector(Employee recruit) throws Exception {
        if(recruit.getNetSalary() < 0){
            throw new Exception("Net Salary must be positive !!!");
        }
        Optional<Director> randomDirector = directorRepository.findRandomDirector();
        if(randomDirector.isEmpty()) throw new Exception("No Record Found in Directors Table !!! Please Add an Director");
        recruit.setDirector(randomDirector.get());
        employeeRepository.save(recruit);
    }

    @Transactional
    public void addNewDirector(Director director){
        directorRepository.save(director);
    }

    @Transactional
    public void dismissRecruit(Long employeeId) throws Exception {
        if(checkEmployeeIdExists(employeeId))
            employeeRepository.deleteEmployeeById(employeeId);
    }

    public void giveRaise(Long employeeId, double rate) throws Exception {
        if(rate < 0) throw new Exception("Rate must be greater than zero !!!");
        if(checkEmployeeIdExists((employeeId))){
            Optional<Employee> employee = employeeRepository.findEmployeeById(employeeId);
            Employee employeeObject = employee.get();
            employeeObject.setNetSalary(employeeObject.getNetSalary() + (employeeObject.getNetSalary() * rate));
            employeeRepository.save(employeeObject);
        }
    }

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
                    creditAccount -> {
                        creditAccount.setBalance(creditAccount.getBalance() * (1 + creditAccount.getInterestRate()));
                        creditAccount.setMonthlyMinimumPayed(false);
                    }
            )
        );

    }

    public Director getDirectorById(long id) throws Exception {
        Optional<Director> directorById = directorRepository.getDirectorById(id);
        if(directorById.isEmpty())
            throw new Exception("Director Id Not Found");
        else return directorById.get();
    }
}
