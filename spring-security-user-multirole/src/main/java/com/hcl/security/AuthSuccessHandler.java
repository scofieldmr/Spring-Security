package com.hcl.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("Authentication successful for user: " + authentication.getName());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));

        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            response.sendRedirect("/api/admin/home");
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_MODERATOR"))) {
            response.sendRedirect("/api/mod/home");
        }
        else{
            response.sendRedirect("/api/user/home");
        }

    }
}
