package com.ms.springsecuritydemo2usingh2.controller;

import com.ms.springsecuritydemo2usingh2.dto.UserRequest;
import com.ms.springsecuritydemo2usingh2.dto.UserResponse;
import com.ms.springsecuritydemo2usingh2.service.CustomUserDetailService;
import com.ms.springsecuritydemo2usingh2.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newUser")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Validated({Default.class}) UserRequest userRequest) {
        UserResponse newUserResp = userService.createUser(userRequest);
        return new ResponseEntity<>(newUserResp, HttpStatus.CREATED);
    }

}
