package com.example.fullstackspringreactbankingapp.controller;

import com.example.fullstackspringreactbankingapp.dto.EmployeeController.CreateAccountDto;
import com.example.fullstackspringreactbankingapp.dto.EmployeeController.DeleteAccountDto;
import com.example.fullstackspringreactbankingapp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/delete_account")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountDto deleteAccountDto){
        try{
            employeeService.deleteAccount(deleteAccountDto.getAccountId(), deleteAccountDto.getAccountType());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok(deleteAccountDto.getAccountType() + " Account Deleted with ID=" + deleteAccountDto.getAccountId());
    }

    @PostMapping("/create_account")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDto createAccountDto){
        try{
            Long accountId = employeeService.createAccount(createAccountDto.getUserId(), createAccountDto.getAccountType());
            return ResponseEntity.ok("Account ID is " + accountId);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/check_user_id")
    public String checkUserId(@RequestParam("id") long id) throws Exception {
        try{
            employeeService.checkUserId(id);
        }catch (Exception ex){
            return "Not Exists";
        }
        return "Exists";
    }
}
