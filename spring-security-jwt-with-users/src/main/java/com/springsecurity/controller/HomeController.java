package com.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {


    @GetMapping("/home")
    public String defaulthome() {
        return "Hello User. Welcome to the Default home";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "Hello Admin. Welcome to the Admin home";
    }

    @GetMapping("/user/home")
    public String userhome() {
        return "Hello User. Welcome to the User home";
    }

    @GetMapping("/mod/home")
    public String modHome() {
        return "Hello Moderator. Welcome to the Moderator home";
    }


}
