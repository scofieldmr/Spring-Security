package com.springsecurity.service;

import com.springsecurity.entity.CustomUserDetails;
import com.springsecurity.entity.MyUser;
import com.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyUser> user = userRepository.findByUsername(username);

        var userObj = user.get();

        if (userObj == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return CustomUserDetails.build(userObj);

//        return new org.springframework.security.core.userdetails.User(userObj.getUsername(), userObj.getPassword(),
//                userObj.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()));

//        List<GrantedAuthority> authorities = userObj.getRoles().stream()
//                .map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//
//        return User.builder()
//                .username(userObj.getUsername())
//                .password(userObj.getPassword())
//                .roles(String.valueOf(authorities))
//                .build();
    }
}
