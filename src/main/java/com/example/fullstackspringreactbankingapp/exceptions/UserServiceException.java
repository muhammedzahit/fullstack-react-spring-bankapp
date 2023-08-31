package com.example.fullstackspringreactbankingapp.exceptions;

public enum UserServiceException {
    WithdrawMoneyMoreThanBalance("You cannot withdraw money more than your balance !!! You can create Credit Account for this purpose"),
    WithdrawMoneyMoreThanLimit("You cannot withdraw money more than your account limit !!!"),
    UserIdNotFound("User ID Not Found ! Please Check Given User ID !!!"),
    WrongAccountOwnership("This Account Not Belong To Given User !!!"),
    CreditAccountIDNotFound("Given Account ID Not Found Among Credit Accounts !!!"),
    SavingAccountIDNotFound("Given Account ID Not Found Among Saving Accounts !!!")
    ;

    private final String value;

    UserServiceException(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
