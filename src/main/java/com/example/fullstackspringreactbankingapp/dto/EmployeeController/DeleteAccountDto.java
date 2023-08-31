package com.example.fullstackspringreactbankingapp.dto.EmployeeController;

import com.example.fullstackspringreactbankingapp.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteAccountDto {
    private long accountId;
    private AccountType accountType;
}
