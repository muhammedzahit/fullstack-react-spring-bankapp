package com.example.fullstackspringreactbankingapp.services;

import com.example.fullstackspringreactbankingapp.dto.Authentication.AccountRegisterDto;
import com.example.fullstackspringreactbankingapp.entities.TokenUser;
import com.example.fullstackspringreactbankingapp.repositories.TokenUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUserService {
    private final TokenUserRepository tokenUserRepository;

    @Transactional
    public void addTokenUser(AccountRegisterDto accountRegisterDto){
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUsername(accountRegisterDto.getEmail());
        tokenUser.setPassword(accountRegisterDto.getPassword());
        tokenUser.setRole(accountRegisterDto.getRole());

        tokenUserRepository.save(tokenUser);
    }
}
