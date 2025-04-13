package com.springsecurity.controller;

import com.springsecurity.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private final JwtUtils jwtUtils;

    public HomeController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

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

    @GetMapping("/protected-data")
    public ResponseEntity<String> protecteddata(@RequestHeader("Authorization") String token) {

        if(token!=null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            try {
                if(jwtUtils.validateToken(jwtToken)) {
                    String username = jwtUtils.extractUsernameFromToken(jwtToken);

                    Set<String> role = jwtUtils.extractRolesFromToken(jwtToken);

                    if(role.contains("ROLE_ADMIN")) {
                        return ResponseEntity.ok().body("Welcome " + username + " to the Admin Page. Roles "+role);
                    } else if (role.contains("ROLE_USER")) {
                        return ResponseEntity.ok().body("Welcome " + username + " to the User Page. Roles "+role);
                    }
                    else{
                        return ResponseEntity.status(403).body("Access Denied.");
                    }
                }
            }
            catch(Exception e) {
                return new ResponseEntity<>("Forbidden Content", HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>("UnAuthorized User", HttpStatus.UNAUTHORIZED);
    }


}
