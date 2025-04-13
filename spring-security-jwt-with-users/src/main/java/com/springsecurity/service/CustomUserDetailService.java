package com.springsecurity.service;

import com.springsecurity.entity.CustomUserDetails;
import com.springsecurity.entity.MyUsers;
import com.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUsers> existingUser = userRepository.findByUsername(username);

        var userObj = existingUser.get();

        if (userObj == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return CustomUserDetails.build(userObj);
    }
}
