package com.example.fullstackspringreactbankingapp;

import com.example.fullstackspringreactbankingapp.dto.Authentication.AccountRegisterDto;
import com.example.fullstackspringreactbankingapp.dto.Authentication.AuthenticationRegisterDto;
import com.example.fullstackspringreactbankingapp.entities.*;
import com.example.fullstackspringreactbankingapp.enums.AccountType;
import com.example.fullstackspringreactbankingapp.jwtAuthentication.AuthenticationService;
import com.example.fullstackspringreactbankingapp.jwtAuthentication.Role;
import com.example.fullstackspringreactbankingapp.repositories.UserRepository;
import com.example.fullstackspringreactbankingapp.services.DirectorService;
import com.example.fullstackspringreactbankingapp.services.EmployeeService;
import com.example.fullstackspringreactbankingapp.services.TokenUserService;
import com.example.fullstackspringreactbankingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final AuthenticationService authenticationService;

    @Override
    public void run(String... args) throws Exception {

        authenticationService.registerAccount(
                AccountRegisterDto.builder().email("test_director@gmail.com").netSalary(1000000.0).password("test_director").role(Role.DIRECTOR)
                        .fullName("Test Director").build()
        );

        authenticationService.registerAccount(
                AccountRegisterDto.builder().email("test_employee@gmail.com").netSalary(32500.0).password("test_employee").role(Role.EMPLOYEE)
                        .fullName("Test Employee").build()
        );

        authenticationService.registerAccount(
                AccountRegisterDto.builder().email("test_user@gmail.com").netSalary(17500.0).password("test_user").role(Role.USER)
                        .fullName("Test USER").build()
        );

    }

}
