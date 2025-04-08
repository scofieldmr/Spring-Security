package com.ms.springsecuritydemo2usingh2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    public String home() {
        return "Hello.. Welcome to Default Home Page.. Accessed by all the Users";
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome() {
        return "Hello.. Welcome to the Admin Home Page.. Accessed by Admin Users";
    }

    @GetMapping("/mod/home")
    @PreAuthorize("hasRole('MODERATOR')")
    public String modHome() {
        return "Hello.. Welcome to the Mod Home Page.. Accessed by Mod Users";
    }

    @GetMapping("/user/home")
    @PreAuthorize("hasRole('USER')")
    public String userHome() {
        return "Hello.. Welcome to the User Home Page.. Accessed by Users";
    }

}
