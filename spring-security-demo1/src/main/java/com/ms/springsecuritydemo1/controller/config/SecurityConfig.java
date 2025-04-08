package com.ms.springsecuritydemo1.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.user.name}")
    private String modUsername;

    @Value("${spring.security.user.password}")
    private String modPassword;

    @Value("${spring.security.user.roles}")
    private String modRole;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user123")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin123")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails mod = User.builder()
                .username(modUsername)
                .password(passwordEncoder().encode(modPassword))
                .roles(modRole)
                .build();

        return new InMemoryUserDetailsManager(user,admin,mod);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((registry)->{
                    registry
                            .requestMatchers("/api/home").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/user/**").hasRole("USER")
                            .requestMatchers("/api/mod/**").hasRole("MODERATOR")
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
               .formLogin((login)-> login.permitAll())
                .logout((logout)->logout.permitAll())
                .build();
    }


}
