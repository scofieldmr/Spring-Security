package com.ms.springsecuritydemo2usingh2.service;

import com.ms.springsecuritydemo2usingh2.dto.UserRequest;
import com.ms.springsecuritydemo2usingh2.dto.UserResponse;
import com.ms.springsecuritydemo2usingh2.entity.Roles;
import com.ms.springsecuritydemo2usingh2.entity.Users;
import com.ms.springsecuritydemo2usingh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        Users newUser = new Users();

        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new RuntimeException("Username already exists :"+ userRequest.getUsername());
        }

        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setEmail(userRequest.getEmail());
        if(userRequest.getRole().equalsIgnoreCase("ADMIN")) {
            newUser.setRole(Roles.ADMIN);
        }
        else if(userRequest.getRole().equalsIgnoreCase("MODERATOR")) {
            newUser.setRole(Roles.MODERATOR);
        }
        else{
            newUser.setRole(Roles.USER);
        }

        userRepository.save(newUser);

        return new UserResponse(newUser.getUsername(), newUser.getRole().name());
    }

}
