package com.example.fullstackspringreactbankingapp.dto.Authentication;

import com.example.fullstackspringreactbankingapp.jwtAuthentication.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountRegisterDto {
    private String fullName;
    private String email;
    private String password;
    private Double netSalary;
    private Role role;
}
