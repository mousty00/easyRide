package com.example.easyRide.auth;

import com.example.easyRide.dto.auth.UserLoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private SecretKey SECRET_KEY;

    @Value("${jwt.secret}")
    private String key;

    @PostConstruct
    public void afterInit(){
        this.SECRET_KEY = Keys.hmacShaKeyFor(key.getBytes());
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractFirstNameFromClaims(final String token) {
        return extractClaim(token, claims -> claims.get("firstName", String.class));
    }


    public <R> R extractClaim(final String token, Function<Claims, R> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    public Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(final UserLoginDTO request) {
        final Map<String, String> claims = new HashMap<>();
        claims.put("lastName", request.getLastName());

        return createToken(claims, request.getFirstName());
    }

    private String createToken(final Map<String, String> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 36000000)) // token last an hour
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean isValid(String token) {
            final Claims claims = extractAllClaims(token);
            if(claims == null) return Boolean.FALSE;
            if (extractExpiration(token).equals(claims.getExpiration())) return Boolean.TRUE;
            return Boolean.FALSE;
    }
}