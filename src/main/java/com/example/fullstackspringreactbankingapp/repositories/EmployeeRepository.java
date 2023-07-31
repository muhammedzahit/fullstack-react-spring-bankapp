package com.example.fullstackspringreactbankingapp.repositories;

import com.example.fullstackspringreactbankingapp.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
