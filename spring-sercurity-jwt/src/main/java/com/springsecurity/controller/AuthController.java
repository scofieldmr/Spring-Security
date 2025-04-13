package com.springsecurity.controller;

import com.springsecurity.dto.LoginRequest;
import com.springsecurity.dto.RegisterRequest;
import com.springsecurity.entity.MyUser;
import com.springsecurity.entity.Roles;
import com.springsecurity.jwt.JwtUtils;
import com.springsecurity.repository.RoleRepository;
import com.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/signup")
    public ResponseEntity<String> registerNewUser(@RequestBody RegisterRequest registerRequest) {

        if (registerRequest.getUsername() == null || registerRequest.getUsername().isBlank()) {
            return new ResponseEntity<>("Username cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        if (registerRequest.getPassword() == null || registerRequest.getPassword().isBlank()) {
            return new ResponseEntity<>("Password cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Optional<MyUser> userExists = userRepository.findByUsername(registerRequest.getUsername());

        if (userExists.isPresent()) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }

        MyUser newUser = new MyUser();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<String> inpRoles = registerRequest.getRoles();
        Set<Roles> roles = new HashSet<>();

        for(String role : inpRoles) {
            Roles roleExists = roleRepository.findByName(role)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(roleExists);
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);
        return new ResponseEntity<>("User successfully registered with Role : "+ newUser.getRoles(), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        }
        catch (Exception e) {
            System.out.println("Exception : "+ e.getMessage());
        }

        String jwtToken = jwtUtils.generateToken(loginRequest.getUsername());

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

}
