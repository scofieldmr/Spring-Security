package com.springsecurity.service;

import com.springsecurity.dto.*;
import com.springsecurity.entity.MyUsers;
import com.springsecurity.entity.Roles;
import com.springsecurity.exception.EmailAlreadyExistsException;
import com.springsecurity.exception.RoleNotFoundException;
import com.springsecurity.exception.UsernameAlreadyExistsException;
import com.springsecurity.jwt.JwtUtils;
import com.springsecurity.repository.RoleRepository;
import com.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SignupResponse createNewUser(SignupRequest signupRequest){

        Optional<MyUsers> userExists = userRepository.findByUsername(signupRequest.getUsername());

        if(userExists.isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists with username: " + signupRequest.getUsername());
        }

        Optional<MyUsers> emailExists = userRepository.findByEmail(signupRequest.getEmail());

        if (emailExists.isPresent()){
            throw new EmailAlreadyExistsException("Email already exists with email: " + signupRequest.getEmail());
        }

        MyUsers newUser = new MyUsers();

        newUser.setFullname(signupRequest.getFullname());
        newUser.setUsername(signupRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setEmail(signupRequest.getEmail());

        Set<String> inputRoles = signupRequest.getRoles();

        Set<Roles> roles = new HashSet<>();

        for(String inRole: inputRoles){
            Roles role = roleRepository.findByName(inRole)
                    .orElseThrow(()-> new RoleNotFoundException("Invalid role: " + inRole));

            roles.add(role);
        }
        newUser.setRoles(roles);
        userRepository.save(newUser);

        Set<String> rolesSet = roles.stream().map(r -> r.getName()).collect(Collectors.toSet());

        return new SignupResponse(newUser.getUsername(),rolesSet);
    }

    public LoginResponse login(LoginRequest loginRequest){
        Optional<MyUsers> userExists = userRepository.findByUsername(loginRequest.getUsername());

        var user = userExists.isPresent()?userExists.get():null;

        if(user == null){
            throw new UsernameNotFoundException("Username not found with username: " + loginRequest.getUsername());
        }

        if(user.getPassword().equals(passwordEncoder.encode(loginRequest.getPassword()))){
            Set<String> rolesSet = user.getRoles().stream()
                    .map(r -> r.getName()).collect(Collectors.toSet());

            return new LoginResponse(user.getFullname(), rolesSet);
        }

        throw new UsernameNotFoundException("Invalid username or password");

    }

    public JwtResponse jwtLogin(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwtToken = jwtUtils.generateToken(authentication);

        String username = jwtUtils.extractUsernameFromToken(jwtToken);

        Optional<MyUsers> userExists = userRepository.findByUsername(username);

        Set<String> roles = jwtUtils.extractRolesFromToken(jwtToken);

        return new JwtResponse(username,roles,"Bearer",jwtToken);
    }

    public JwtResponse protectedDataBasedOnRole(String token){
        if(token!=null && token.startsWith("Bearer")){
            String jwtToken = token.substring(7);

            try{
                if (jwtUtils.validateToken(jwtToken)) {
                    String username = jwtUtils.extractUsernameFromToken(jwtToken);

                    Set<String> roles = jwtUtils.extractRolesFromToken(jwtToken);

                    logger.info("Username : " + username + " has roles : " + roles.toString());

                    return new JwtResponse(username,roles,"Bearer",jwtToken);
                }
            }
            catch (Exception e){
                throw new RuntimeException("Forbidden Content..");
            }

        }

        return null;
    }

}
