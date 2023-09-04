package com.example.fullstackspringreactbankingapp.jwtAuthentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.fullstackspringreactbankingapp.jwtAuthentication.Permission.*;


@RequiredArgsConstructor
public enum Role {

    USER(Set.of(
            READ_MY_ACCOUNT,
            UPDATE_MY_ACCOUNT,
            DELETE_MY_ACCOUNT,
            CREATE_NEW_ACCOUNT
    )),
    EMPLOYEE(
            Set.of(
                    READ_ALL_ACCOUNTS,
                    DELETE_ALL_COUNTS,
                    CREATE_NEW_ACCOUNT_FOR_ALL
            )
    ),
    DIRECTOR(
            Set.of(
                    HIRE_NEW_RECRUIT,
                    DISMISS_RECRUIT,
                    FULLFILL_MONTHLY_OPERATION
            )
    ),
    ADMIN(
            Set.of(
                    FULL_ACCESS
            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}