package com.springsecurity.controller;

import com.springsecurity.dto.*;
import com.springsecurity.service.CustomUserDetailService;
import com.springsecurity.service.UserService;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Validated({Default.class}) SignupRequest signupRequest){
        SignupResponse newUser = userService.createNewUser(signupRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated({Default.class}) LoginRequest loginRequest){
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/jwtlogin")
    public ResponseEntity<JwtResponse> jwtLogin(@RequestBody @Validated({Default.class}) LoginRequest loginRequest){
        JwtResponse jwtResponse = userService.jwtLogin(loginRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/protected-data")
    public ResponseEntity<String> protectedData(@RequestHeader("Authorization") String token) {

        JwtResponse response = userService.protectedDataBasedOnRole(token);

        System.out.println("username : " + response.getUsername());
        System.out.println("roles : " + response.getRoles());

        if(response.getRoles().contains("ROLE_ADMIN")){
            return new ResponseEntity<>("Welcome to the admin Page : "+ response.getUsername(), HttpStatus.OK);
        }
        else if(response.getRoles().contains("ROLE_USER")){
            return new ResponseEntity<>("Welcome to the User Page : "+ response.getUsername(), HttpStatus.OK);
        }
        else if(response.getRoles().contains("ROLE_MODERATOR")){
            return new ResponseEntity<>("Welcome to the Mod Page : "+ response.getUsername(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Unauthorized ", HttpStatus.UNAUTHORIZED);
        }

    }




}
