package com.ms.springsecuritydemo2usingh2.controller.config;

import com.ms.springsecuritydemo2usingh2.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((registry)->{
                    registry
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/api/home").permitAll()
                            .requestMatchers("/api/newUser/**").permitAll()
                            .anyRequest().authenticated();
                })
                .headers(header-> header.frameOptions(frame-> frame.sameOrigin()))
                // Basic login
                .httpBasic(Customizer.withDefaults())
                .formLogin((login)-> login.permitAll())
                .logout((logout)->logout.permitAll())
                 // TO disable the cookies which was storing the JsessionIds
                .sessionManagement((session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder()
//                .username("admin1")
//                .password(passwordEncoder().encode("admin1"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails mod = User.builder()
//                .username("mod1")
//                .password(passwordEncoder().encode("mod1"))
//                .roles("MODERATOR")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("user1"))
//                .roles("USER")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(admin,mod,user);
//    }
}
