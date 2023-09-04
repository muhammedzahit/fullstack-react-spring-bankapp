package com.example.fullstackspringreactbankingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    @OneToOne
    @JoinColumn(name = "token_user_id")
    private TokenUser tokenUser;

    private Double netSalary;

    @OneToMany(mappedBy = "director")
    private List<Employee> employees;
}
