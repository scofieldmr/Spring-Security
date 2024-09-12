package com.hcl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public String getHome(){
        return "Welcome to Spring Boot Application.. General Home";
    }

    @GetMapping("/admin/home")
    public String getAdminHome(){
        return "Welcome to the Admin Page";
    }

    @GetMapping("/user/home")
    public String getUserHome(){
        return "Welcome to the User Page";
    }
}
