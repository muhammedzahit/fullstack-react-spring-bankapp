package com.example.fullstackspringreactbankingapp.jwtAuthentication;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    FULL_ACCESS("full_access"),

    READ_MY_ACCOUNT("read_my_account"),

    UPDATE_MY_ACCOUNT("update_my_account"),

    CREATE_NEW_ACCOUNT("create_new_account"),

    DELETE_MY_ACCOUNT("delete_my_account"),

    READ_ALL_ACCOUNTS("read_all_accounts"),

    DELETE_ALL_COUNTS("delete_all_accounts"),

    CREATE_NEW_ACCOUNT_FOR_ALL("create_new_account_for_all"),

    HIRE_NEW_RECRUIT("hire_new_recruit"),

    DISMISS_RECRUIT("dismiss_recruit"),

    FULLFILL_MONTHLY_OPERATION("fulfill_monthly_operation"),

    ;

    @Getter
    private final String permission;
}