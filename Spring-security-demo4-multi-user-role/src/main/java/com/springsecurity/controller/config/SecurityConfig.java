package com.springsecurity.controller.config;

import com.springsecurity.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((registry) -> {
                    registry
                            .requestMatchers("/api/newUser/create","/api/home").permitAll()
                            .requestMatchers("/api/admin/**","page/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/user/**","page/user/**").hasRole("USER")
                            .requestMatchers("/api/mod/**","page/mod/**").hasRole("MODERATOR")
                            .anyRequest().authenticated();
                })
                .formLogin((login)-> {
                    login
                            .successHandler(new AuthenticationSuccessHandler())
                            .permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();

    }
}
