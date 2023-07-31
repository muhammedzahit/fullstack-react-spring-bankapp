package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
