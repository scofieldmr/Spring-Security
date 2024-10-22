package com.hcl.service;

import com.hcl.entity.CustomUserDetail;
import com.hcl.entity.MyUsers;
import com.hcl.repository.UserRepository;
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
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUsers user =  userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("Username : "+ username + " not found..");
        }

        return new CustomUserDetail().build(user);
    }
}
