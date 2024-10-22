package com.hcl.controller;


import com.hcl.entity.ERole;
import com.hcl.entity.MyUsers;
import com.hcl.entity.Role;
import com.hcl.repository.RoleRepository;
import com.hcl.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody MyUsers users){

        boolean isUserExists = userRepository.existsByUsername(users.getUsername());

        if(isUserExists){
            return ResponseEntity.badRequest()
                    .body("Username is already taken.. Kindly use different name");
        }

        MyUsers regUser = new MyUsers();

        regUser.setUsername(users.getUsername());

        regUser.setPassword(passwordEncoder.encode(users.getPassword()));

        Set<Role> strRole = users.getRoles();

        Set<Role> roles = new HashSet<>();

        for(Role userRole : strRole){
            Role role = roleRepository.findByName(userRole.getName());
            if(role!=null) {
                roles.add(role);
            }
            else {
                return ResponseEntity.badRequest().body("Role not found: " + userRole.getName());
            }
        }

        regUser.setRoles(roles);

        userRepository.save(regUser);


        // Debugging logs
        System.out.println("User registered: " + regUser.getUsername());
        System.out.println("Roles assigned: " + regUser.getRoles());
        System.out.println("Password : "+ regUser.getPassword());

        return ResponseEntity.ok().body("Username Registered Successfully....");

    }


}
