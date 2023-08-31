package com.example.fullstackspringreactbankingapp.exceptions;

public enum EmployeeServiceException {
    EmployeeIdNotFound("Employee Id Not Found, Please Check Given ID !!!"),
    NoCreditAccountFound("No Credit Account Found With This ID !!!"),
    NoSavingAccountFound("No Saving Account Found With This ID !!!")
    ;
    private final String value;

    EmployeeServiceException(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
