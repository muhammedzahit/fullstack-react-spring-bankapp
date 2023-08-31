package com.example.fullstackspringreactbankingapp.dto.UserController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayMinimumRateDto {
    private double money;
    private long userId;
    private long accountId;
}
