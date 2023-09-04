package com.example.fullstackspringreactbankingapp.controller;

import com.example.fullstackspringreactbankingapp.dto.UserController.*;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/deposit")
    @PreAuthorize("hasAnyAuthority('update_my_account', 'full_access')")
    public ResponseEntity<String> depositMoney(@RequestBody DepositMoneyDto depositMoneyDto){
        try{
            userService.depositMoneyToAccount(depositMoneyDto.getMoney(), depositMoneyDto.getUserId(),
                    depositMoneyDto.getAccountId(), depositMoneyDto.getAccountType());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Money added to your account, please check your account");
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAnyAuthority('update_my_account', 'full_access')")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawMoneyDto withdrawMoneyDto){
        try{
            userService.withdrawMoneyFromAccount(withdrawMoneyDto.getMoney(), withdrawMoneyDto.getUserId(),
                    withdrawMoneyDto.getAccountId(), withdrawMoneyDto.getAccountType());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Funds have been debited from your account, please check your account");
    }

    @PostMapping("/enable_saving")
    @PreAuthorize("hasAnyAuthority('update_my_account', 'full_access')")
    public ResponseEntity<String> enableSaving(@RequestBody EnableSavingDto enableSavingDto){
        try{
            userService.enableSaving(enableSavingDto.getUserId(), enableSavingDto.getAccountId());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Saving Enabled for Given Account ID");
    }

    @PostMapping("/pay_minimum_rate")
    @PreAuthorize("hasAnyAuthority('update_my_account', 'full_access')")
    public ResponseEntity<String> payMinimumRate(@RequestBody PayMinimumRateDto payMinimumRateDto){
        try{
            userService.payMinimumRateOfCredit(payMinimumRateDto.getMoney(), payMinimumRateDto.getUserId(), payMinimumRateDto.getAccountId());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("You paid minimum rate of credit successfully. If you deposit money more than your minimum rate it will be added into your balance");
    }

    @GetMapping("/no_need_permission")
    public String hello(){
        return "Hello world";
    }
}
