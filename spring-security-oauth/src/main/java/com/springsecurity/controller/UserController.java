package com.springsecurity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @GetMapping("/details")
    public Map<String,Object> userDetails(@AuthenticationPrincipal OAuth2User principal) {
        Map<String,Object> user = new HashMap<>();
        user.put("name",principal.getAttribute("name"));
        user.put("email",principal.getAttribute("email"));
        user.put("picture",principal.getAttribute("picture"));
        return user;
    }
}
