package com.example.fullstackspringreactbankingapp.jwtAuthentication;


import com.example.fullstackspringreactbankingapp.dto.Authentication.*;
import com.example.fullstackspringreactbankingapp.entities.Director;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.entities.TokenUser;
import com.example.fullstackspringreactbankingapp.entities.User;
import com.example.fullstackspringreactbankingapp.repositories.TokenUserRepository;
import com.example.fullstackspringreactbankingapp.services.DirectorService;
import com.example.fullstackspringreactbankingapp.services.TokenUserService;
import com.example.fullstackspringreactbankingapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final DirectorService directorService;
    private final TokenUserService tokenUserService;

    public RegisterResponseDto register(AuthenticationRegisterDto request) {
        TokenUser user = modelMapper.map(request, TokenUser.class);
        user.setUsername(request.getTokenUsername());
        user.setPassword(passwordEncoder.encode(request.getTokenPassword()));

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return RegisterResponseDto.builder()
                .tokenUserId(savedUser.getId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ResponseEntity<RegisterResponseDto> registerAccount(AccountRegisterDto registerDto) throws Exception {
        //tokenUserService.addTokenUser(registerDto);
        AuthenticationRegisterDto authenticationRegisterDto = new AuthenticationRegisterDto();
        authenticationRegisterDto.setRole(registerDto.getRole());
        authenticationRegisterDto.setTokenUsername(registerDto.getEmail());
        authenticationRegisterDto.setTokenPassword(registerDto.getPassword());
        RegisterResponseDto response = this.register(authenticationRegisterDto);

        if(registerDto.getRole() == Role.USER){
            User map = modelMapper.map(registerDto, User.class);
            Optional<TokenUser> tokenUserById = repository.getTokenUserById(response.getTokenUserId());
            if(tokenUserById.isPresent()){
                map.setTokenUser(tokenUserById.get());
                userService.addUserWithRandomResponsible(map);
            }
        }
        else if(registerDto.getRole() == Role.EMPLOYEE){
            Employee map = modelMapper.map(registerDto, Employee.class);
            Optional<TokenUser> tokenUserById = repository.getTokenUserById(response.getTokenUserId());
            if(tokenUserById.isPresent()){
                map.setTokenUser(tokenUserById.get());
                directorService.addNewRecruitWithRandomDirector(map);
            }
        }
        else if(registerDto.getRole() == Role.DIRECTOR){
            Director map = modelMapper.map(registerDto, Director.class);
            Optional<TokenUser> tokenUserById = repository.getTokenUserById(response.getTokenUserId());
            if(tokenUserById.isPresent()){
                map.setTokenUser(tokenUserById.get());
                directorService.addNewDirector(map);
            }
        }
        else if(registerDto.getRole() == Role.ADMIN) {}

        return ResponseEntity.ok(response);
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getTokenUsername(),
                        request.getTokenPassword()
                )
        );
        var user = repository.findByUsername(request.getTokenUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder().success(true)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(TokenUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(TokenUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameInfo;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        usernameInfo = jwtService.extractUsername(refreshToken);
        if (usernameInfo != null) {
            var user = this.repository.findByUsername(usernameInfo)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
