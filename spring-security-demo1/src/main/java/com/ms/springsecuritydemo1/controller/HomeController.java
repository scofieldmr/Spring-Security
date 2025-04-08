package com.ms.springsecuritydemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public String defaulthome() {
        return "Default home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin home";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "user home";
    }

    @GetMapping("/mod/home")
    public String modHome() {
        return "Moderator home";
    }

}
