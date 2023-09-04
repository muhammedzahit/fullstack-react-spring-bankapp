package com.example.fullstackspringreactbankingapp.controller;


import com.example.fullstackspringreactbankingapp.dto.Authentication.*;
import com.example.fullstackspringreactbankingapp.entities.Director;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.entities.TokenUser;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.jwtAuthentication.AuthenticationService;
import com.example.fullstackspringreactbankingapp.jwtAuthentication.Role;
import com.example.fullstackspringreactbankingapp.services.DirectorService;
import com.example.fullstackspringreactbankingapp.services.EmployeeService;
import com.example.fullstackspringreactbankingapp.services.TokenUserService;
import com.example.fullstackspringreactbankingapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;
    private final DirectorService directorService;
    private final ModelMapper modelMapper;
    private final TokenUserService tokenUserService;

    @GetMapping("/test_permission")
    public String testPermission() {return "You are permitted";}

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        AuthenticationResponseDto dto;
        try{
            dto = service.authenticate(request);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(AuthenticationResponseDto.builder().success(false).accessToken("-1").refreshToken("-1").build());
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/refresh_token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/register_token")
    public ResponseEntity<RegisterResponseDto> registerToken(
            @RequestBody AuthenticationRegisterDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody AccountRegisterDto registerDto) throws Exception {
        ResponseEntity<RegisterResponseDto> registerResponseDtoResponseEntity = service.registerAccount(registerDto);
        return ResponseEntity.ok(registerResponseDtoResponseEntity.getBody());
    }


}
