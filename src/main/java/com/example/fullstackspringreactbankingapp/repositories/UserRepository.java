package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserById(Long id);
    boolean existsUserById(Long id);
}
