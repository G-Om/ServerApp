package com.server.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor  // Creates a const automatically from any final field declared
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("authorization");
        final String jwt;   // contains actual JWT Token
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);  // returns a substring by eliminating Bearar and an extra space

        String clientRole = jwtService.extractUsername(jwt);
        if(clientRole != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("role found");
             if(jwtService.isTokenValid(jwt)){
                 System.out.println("Token Valid");
                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                         jwt,
                         "",
                         null
                 );
                 authToken.setDetails(
                         new WebAuthenticationDetailsSource().buildDetails(request)
                 );
                 SecurityContextHolder.getContext().setAuthentication(authToken);
                 System.out.println(SecurityContextHolder.getContext());
             }
        }
        filterChain.doFilter(request, response);
    }
}
