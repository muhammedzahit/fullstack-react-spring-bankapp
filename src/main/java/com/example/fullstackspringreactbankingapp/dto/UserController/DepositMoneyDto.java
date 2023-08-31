package com.example.fullstackspringreactbankingapp.dto.UserController;

import com.example.fullstackspringreactbankingapp.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepositMoneyDto {
    private long userId;
    private long accountId;
    private double money;
    private AccountType accountType;
}
