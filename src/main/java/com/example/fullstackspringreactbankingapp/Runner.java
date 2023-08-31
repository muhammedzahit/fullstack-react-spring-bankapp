package com.example.fullstackspringreactbankingapp;

import com.example.fullstackspringreactbankingapp.entities.*;
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

        // We will create a new Director
        Director director = Director.builder().fullName("Admin").email("admin@gmail.com").netSalary(1000000.00).password("admin").build();
        directorService.addNewDirector(director);
    }

}
