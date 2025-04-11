package com.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class HomePageController {

    @GetMapping("/home")
    public String defaultHome(){
        return "defaulthome";
    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "admin_home";
    }

    @GetMapping("/mod/home")
    public String modHome(){
        return "mod_home";
    }

    @GetMapping("/user/home")
    public String userHome(){
        return "user_home";
    }
}
