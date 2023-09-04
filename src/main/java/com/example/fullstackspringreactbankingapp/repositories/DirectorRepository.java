package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.Director;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
    Optional<Director> getDirectorById(long id);

    @Query(value = "SELECT * from directors order by RANDOM() limit 1", nativeQuery = true)
    Optional<Director> findRandomDirector();
}
