package com.springsecurity.service;

import com.springsecurity.Repository.UserRepository;
import com.springsecurity.dto.UserCreateRequest;
import com.springsecurity.dto.UserResponse;
import com.springsecurity.entity.ERoles;
import com.springsecurity.entity.MyUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createNewUser(UserCreateRequest userCreateRequest) {
        Optional<MyUsers> myUsers = userRepository.findByUsername(userCreateRequest.getUsername());

        if (myUsers.isPresent()) {
            throw new RuntimeException("User already exists with username: " + userCreateRequest.getUsername());
        }

        MyUsers myUser = new MyUsers();
        myUser.setUsername(userCreateRequest.getUsername());
        myUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        myUser.setEmail(userCreateRequest.getEmail());

        if (userCreateRequest.getRole().equalsIgnoreCase("admin")) {
            myUser.setRoles(ERoles.ADMIN);
        }
        else if (userCreateRequest.getRole().equalsIgnoreCase("moderator")) {
            myUser.setRoles(ERoles.MODERATOR);
        }
        else{
            myUser.setRoles(ERoles.USER);
        }

        userRepository.save(myUser);

        return new UserResponse(myUser.getUsername(), myUser.getRoles().name());
    }
}
