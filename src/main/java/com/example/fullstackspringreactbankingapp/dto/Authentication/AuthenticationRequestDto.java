package com.example.fullstackspringreactbankingapp.dto.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    private String tokenUsername;
    private String tokenPassword;

}
