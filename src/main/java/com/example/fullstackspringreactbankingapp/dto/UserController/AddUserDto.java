package com.example.fullstackspringreactbankingapp.dto.UserController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddUserDto {
    private String fullName;
    private String email;
    private String password;
    private Double netSalary;
    private long responsibleId;
}
