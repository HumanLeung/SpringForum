package com.company.springforum.security.jwtutil;

import com.company.springforum.security.jwt.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtUtil {
   static String SECRET = "cud3fzpnqr9romxy4jqcpew31vnrr8jtk3u2papez2owurma1";

  static public String generateToken(Authentication authResult) {
        return Jwts.builder().setSubject(authResult.getName())
                 .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
         .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
         .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
         .compact();
    }

    static public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }


    static private Claims extractAllClaims(String token) {
      return Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes())).build()
              .parseClaimsJws(token).getBody();
    }

    static public String extractCredentials(String token) {
      return extractAllClaims(token).getSubject();
    }

    static Date extractExpiration(String token) {
      return extractAllClaims(token).getExpiration();
    }

    static Boolean isTokenExpired(String token) {
     return extractExpiration(token).before(new Date());
    }

    static public Boolean validateToken(String token, UserDetails userDetails) {
     final String credential = extractCredentials(token);
     return (credential.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    static public List<SimpleGrantedAuthority> extractAuthorities(String token) {
       List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Map<String,String>> authorities = (List<Map<String, String>>) extractAllClaims(token).get("authorities");
        for (Map<String,String> authority : authorities ){
             grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("authority")));
        }
        return grantedAuthorities;
    }
}
