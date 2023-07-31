package com.example.fullstackspringreactbankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private Double netSalary;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(mappedBy = "responsible")
    private List<User> users;


}
