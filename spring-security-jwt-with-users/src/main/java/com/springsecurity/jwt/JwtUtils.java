package com.springsecurity.jwt;

import com.springsecurity.entity.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${authentication.jwt.secret}")
    private String secretKey;

    @Value("${authentication.jwt.expirationInMs}")
    private int expirationInMs;


    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    //Generate Token
    public String generateToken(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Set<String> roles = userDetails.getAuthorities()
                .stream().map(r-> r.getAuthority()).collect(Collectors.toSet());

        Date expiration = new Date(new Date().getTime() + expirationInMs);

        logger.info("Generating the toker for the User : " + username);

        logger.info("For the Role : " + roles);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key())
                .compact();
    }

    //Extract Username from Token
    public String extractUsernameFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    //Extract Roles from Token
    public Set<String> extractRolesFromToken(String token) {

        List<String> rolesList = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class); // Get roles as a List

        // Convert the List to a Set if needed
        return new HashSet<>(rolesList); // Return as a Set<String>
    }

    //Valid Token
    public boolean validateToken(String token) {
      try{
          Jwts.parserBuilder().
                  setSigningKey(key()).build()
                  .parseClaimsJws(token);
          return true;
      }

      catch (MalformedJwtException e) {
          logger.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
          logger.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
          logger.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
          logger.error("JWT claims string is empty: {}", e.getMessage());
      }

      return false;
    }
}
