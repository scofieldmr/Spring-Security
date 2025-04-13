package com.springsecurity.jwt;

import com.springsecurity.entity.CustomUserDetails;
import com.springsecurity.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailService customUserDetailService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           logger.info("Attempting JWT authentication");
           String token = request.getHeader("Authorization");

           logger.info("Authorization header: " + token);

           if (token != null && token.startsWith("Bearer ")) {
               token = token.substring(7);

               if (token != null && jwtUtils.validateToken(token)) {
                   String username = jwtUtils.extractUsernameFromToken(token);

                   CustomUserDetails userDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(username);

                   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
           }
       }
       catch (Exception e) {
           logger.error(e.getMessage());
       }

        filterChain.doFilter(request, response);

    }
}
