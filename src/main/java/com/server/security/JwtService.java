package com.server.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "79d50dc70da6c18e58d4f6ea8749ff69f01cb9e7ce062b43a191d62e9b587aa0";
    public String extractUsername(String token) {

        return extractClaim(token, claims -> claims.getSubject());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    // generate token only using name i.e client1
    public String generateToken(){
        return generateToken(new HashMap<>());
    }

    public String generateToken(
            Map<String, Object> extraClaims) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject("client1")      // TODO- Hard coding client id, later fetch from a file
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *3600 *24 *365 *10)) // 10 years
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //currently validating based on hardcoded client is i.e. client1
    public boolean isTokenValid(String token){
        final String userid = extractUsername(token);
        return (userid.equals("client1") && !isTokenExpired());  // validating a hardcoded user
    }

    private boolean isTokenExpired() {
        return false;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


















