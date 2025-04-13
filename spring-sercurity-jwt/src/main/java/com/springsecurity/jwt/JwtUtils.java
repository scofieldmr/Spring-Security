package com.springsecurity.jwt;

import com.springsecurity.entity.MyUser;
import com.springsecurity.entity.Roles;
import com.springsecurity.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    //Secret key
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //Expiration time
    private final int expirationInMs = 8640000;

    @Autowired
    private final UserRepository userRepository;

    public JwtUtils(UserRepository userRepository) {
           this.userRepository = userRepository;
    }

    //Generate Token
    public String generateToken(String username) {

        Optional<MyUser> user = userRepository.findByUsername(username);

        Set<Roles> roles = user.get().getRoles();

        //Add roles in the token
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles.stream().map(role-> role.getName()).collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationInMs))
                .signWith(secretKey)
                .compact();
    }

    //Getting username from token
    public String extractUsernameFromToken(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //Extract roles
    public Set<String> extractRolesFromToken(String token) {

        String roles = Jwts
                .parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("roles", String.class);

        return Set.of(roles);
    }

    public boolean validateToken(String token) {
         try{
             Jwts.parserBuilder()
                     .setSigningKey(secretKey).build()
                     .parseClaimsJws(token);
             return true;
         }
         catch (JwtException | IllegalArgumentException ex){
             return false;
         }
    }





}
