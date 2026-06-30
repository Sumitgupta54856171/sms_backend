package com.example.schoolsystem.config;

import com.example.schoolsystem.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Request se Token nikalna
            String jwt = parseJwt(request);

            // 2. Agar token hai aur valid hai
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // 3. Token se email (username) nikalna
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // 4. Database se user ki details aur roles nikalna
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Spring Security ko batana ki "Yeh user valid hai, isko andar aane do"
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Context mein set karna (Taki @PreAuthorize kaam kar sake)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e.getMessage());
        }

        // 7. Request ko aage badha dena (Next filter ya Controller ke paas)
        filterChain.doFilter(request,response);
    }

    // Helper method: "Bearer <token>" mein se sirf token nikalne ke liye
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
