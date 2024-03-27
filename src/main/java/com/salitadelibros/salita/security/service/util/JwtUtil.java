package com.salitadelibros.salita.security.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${token.secret.word}")
    private String SECRET_WORD;

    public Claims extractClaimsOfToken(String token){
        try{
            return Jwts.parser().setSigningKey(SECRET_WORD).parseClaimsJws(token).getBody();
        } catch (SignatureException e){
            throw new SignatureException("Token has been altered");
        }
    }

    public String extractUsername(String token){
        return extractClaimsOfToken(token).getSubject();
    }

    public Date extractExpirationToken(String token){
        return extractClaimsOfToken(token).getExpiration();
    }

    public boolean isTokenExpiration(String token){
        return extractClaimsOfToken(token).getExpiration().before(new Date());
    }

    public String prepareToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_WORD)
                .compact();
    }

    public String createToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return prepareToken(claims, userDetails.getUsername());
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (!isTokenExpiration(token) && username.equals(userDetails.getUsername()));
    }
}

