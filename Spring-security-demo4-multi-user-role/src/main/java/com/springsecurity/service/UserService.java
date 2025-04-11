package com.springsecurity.service;

import com.springsecurity.dto.RoleDto;
import com.springsecurity.dto.UserCreateRequest;
import com.springsecurity.dto.UserResponse;
import com.springsecurity.entity.ERole;
import com.springsecurity.entity.MyUser;
import com.springsecurity.entity.Role;
import com.springsecurity.exception.RoleNotFoundException;
import com.springsecurity.exception.UsernameAlreadyExistsException;
import com.springsecurity.repository.RoleRepository;
import com.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createNewUser(UserCreateRequest userCreateRequest){

        Optional<MyUser> existUser = userRepository.findByUsername(userCreateRequest.getUsername());

        if(existUser.isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists : " + userCreateRequest.getUsername());
        }

        MyUser myUser = new MyUser();

        myUser.setFirstname(userCreateRequest.getFirstname());
        myUser.setLastname(userCreateRequest.getLastname());
        myUser.setUsername(userCreateRequest.getUsername());
        myUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        myUser.setEmail(userCreateRequest.getEmail());

        //Getting the ROles from Request and
        Set<RoleDto> inpRoleSet = userCreateRequest.getRoles();
        Set<Role> roleSet = new HashSet<>();

        System.out.println("Incoming Roles: " + inpRoleSet);

        for (RoleDto roleDto : inpRoleSet) {
            try {
                ERole erole = ERole.valueOf(roleDto.getName());
                Role role = roleRepository.findByName(erole);

                if (role != null) {
                    roleSet.add(role);
                } else {
                    throw new RoleNotFoundException("Role not found: " + roleDto.getName());
                }

            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role name passed: " + roleDto.getName());
            }
        }

        myUser.setRoles(roleSet);

        userRepository.save(myUser);

        System.out.println("username :" + userCreateRequest.getUsername());
        System.out.println("Roles : "+roleSet.stream().collect(Collectors.toSet()));

        Set<RoleDto> responseRoles = roleSet.stream()
                .map(r -> new RoleDto(r.getName().name()))
                .collect(Collectors.toSet());

        return new UserResponse(myUser.getUsername(), responseRoles);
    }
}
