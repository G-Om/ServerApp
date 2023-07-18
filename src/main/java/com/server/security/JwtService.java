package com.server.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "79d50dc70da6c18e58d4f6ea8749ff69f01cb9e7ce062b43a191d62e9b587aa0";
    public String extractClientid(String token) {
        try{
            final Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey())    // using the Secret Key To Decode and extract Claims from the Jwt-Token
                    .build().parseClaimsJws(token).getBody();

            return claims.getSubject();
        }catch (Exception e){
            System.out.println("Error : "+ e.getMessage());
            return null;
        }
    }

    //currently validating based on hardcoded client is i.e. client1
    public boolean isTokenValid(String token){
        final String clientid = extractClientid(token);

        return (clientid.equals("client1"));  // validating a hardcoded user
        // Token Expiry is automatically checked while extracting claims
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


















