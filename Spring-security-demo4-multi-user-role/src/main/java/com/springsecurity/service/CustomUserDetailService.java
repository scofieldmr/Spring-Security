package com.springsecurity.service;

import com.springsecurity.entity.CustomUserDetail;
import com.springsecurity.entity.MyUser;
import com.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> userExists = userRepository.findByUsername(username);
        var userObj = userExists.get();

        if (userExists.isPresent()) {
            System.out.println(userExists.get().getUsername());
            System.out.println(userExists.get().getRoles());
            return new CustomUserDetail().build(userObj);
        }

        throw new UsernameNotFoundException("User not found with username : " + username);
    }
}
