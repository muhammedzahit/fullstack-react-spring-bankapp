package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.TokenUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenUserRepository extends JpaRepository<TokenUser, Integer> {
    Optional<TokenUser> findByUsername(String username);

    Optional<TokenUser> getTokenUserById(Long id);
}
