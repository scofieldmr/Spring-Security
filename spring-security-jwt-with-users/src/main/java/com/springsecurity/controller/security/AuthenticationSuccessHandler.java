package com.springsecurity.controller.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        boolean isAdmin = authentication.getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        boolean isMod = authentication.getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MODERATOR"));

        boolean isUser = authentication.getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

        if(isAdmin) {
            setDefaultTargetUrl("/api/admin/home");
        }
        else if(isMod) {
            setDefaultTargetUrl("/api/mod/home");
        }
        else if(isUser) {
            setDefaultTargetUrl("/api/user/home");
        }
        else{
            setDefaultTargetUrl("/api/home");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
