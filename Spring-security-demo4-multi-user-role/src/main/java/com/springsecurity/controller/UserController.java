package com.springsecurity.controller;

import com.springsecurity.dto.UserCreateRequest;
import com.springsecurity.dto.UserResponse;
import com.springsecurity.service.UserService;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/newUser/create")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Validated({Default.class})
                                                      UserCreateRequest userCreateRequest){
        UserResponse userResponse = userService.createNewUser(userCreateRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
