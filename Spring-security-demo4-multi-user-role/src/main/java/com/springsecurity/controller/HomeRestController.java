package com.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeRestController {

    @GetMapping("/home")
    public String getHome(){
        return "Hi, Welcome to the Application.......";
    }

    @GetMapping("/admin/home")
    public String getAdminHome(){
        return "Hi Admin, Welcome to the Application.......";
    }

    @GetMapping("/mod/home")
    public String getModeratorHome(){
        return "Hi Moderator, Welcome to the Application.......";
    }

    @GetMapping("/user/home")
    public String getUserHome(){
        return "Hi User, Welcome to the Application.......";
    }
}
