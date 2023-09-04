package com.example.fullstackspringreactbankingapp.dto.Authentication;

import com.example.fullstackspringreactbankingapp.jwtAuthentication.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegisterDto {

    private String tokenUsername;
    private String tokenPassword;
    private Role role;

}
