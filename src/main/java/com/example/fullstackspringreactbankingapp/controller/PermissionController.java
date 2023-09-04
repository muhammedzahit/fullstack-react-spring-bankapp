package com.example.fullstackspringreactbankingapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {

    @GetMapping("/no_need_permission")
    public String helloWorld(){
        return "This request does not need a permission";
    }

    @GetMapping("/read_all_accounts")
    @PreAuthorize("hasAuthority('read_all_accounts')")
    public String checkAdminReadPermission(){
        return "You have READ_ALL_ACCOUNTS permission";
    }

    @GetMapping("/hire_new_recruit")
    @PreAuthorize("hasAuthority('hire_new_recruit')")
    public String checkAdminUpdatePermission(){
        return "You have HIRE_NEW_RECRUIT permission";
    }
}
