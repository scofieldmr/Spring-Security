package com.hcl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public String getHome(){
        return "Hi, Welcome to the Application.......";
    }

    @GetMapping("/admin/home")
//    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminHome(){
        return "Hi Admin, Welcome to the Application.......";
    }

    @GetMapping("/mod/home")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public String getModeratorHome(){
        return "Hi Moderator, Welcome to the Application.......";
    }

    @GetMapping("/user/home")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public String getUserHome(){
        return "Hi User, Welcome to the Application.......";
    }
}
