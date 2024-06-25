package com.qdtas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwt;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHead = request.getHeader("Authorization");
        String token=null;
        String username=null;
        if (tokenHead !=null && tokenHead.startsWith("Bearer ")){
            token =tokenHead.substring(7);
            try {
                username= jwt.extractUsername(token);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = customUserDetailsService.userDetailsService().loadUserByUsername(username);
                UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                uToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(uToken);
            }
            else {
                throw new RuntimeException("Something Went Wrong Try logging in again");
            }
        }
        filterChain.doFilter(request,response);
    }
}
