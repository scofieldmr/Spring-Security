package com.ms.springsecuritydemo2usingh2.service;

import com.ms.springsecuritydemo2usingh2.dto.UserRequest;
import com.ms.springsecuritydemo2usingh2.dto.UserResponse;
import com.ms.springsecuritydemo2usingh2.entity.Roles;
import com.ms.springsecuritydemo2usingh2.entity.Users;
import com.ms.springsecuritydemo2usingh2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userExist = userRepository.findByUsername(username);

        var userobj = userExist.get();

        if(userobj!=null) {
            return User.builder()
                    .username(userobj.getUsername())
                    .password(userobj.getPassword())
                    .roles(userobj.getRole().name())
                    .build();
        }
        else{
            throw new UsernameNotFoundException("Username not found : " + username);
        }
    }

//    public UserResponse createUser(UserRequest user) {
//
//        Users newUser = new Users();
//
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(user.getPassword());
//        newUser.setEmail(user.getEmail());
//
//        if(user.getRole()!=null) {
//            if(user.getRole().equals("ADMIN")) {
//                newUser.setRole(Roles.ROLE_ADMIN);
//            }
//            else if(user.getRole().equals("MODERATOR")) {
//                newUser.setRole(Roles.ROLE_MODERATOR);
//            }
//            else {
//                newUser.setRole(Roles.ROLE_USER);
//            }
//        }
//
//        userRepository.save(newUser);
//
//        return new UserResponse(user.getUsername(), user.getRole());
//    }
}
