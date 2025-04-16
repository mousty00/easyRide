package com.example.easyRide.auth;

import com.example.easyRide.dto.info.ErrorBodyInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
@Order(1)
public class AccessFilter extends OncePerRequestFilter {
    private static final String USERS_ENDPOINT = "/api/users";
    private static final String ACCEPT_ENDPOINT = "/api/vehicles";

    private final JwtTokenProvider jwtTokenProvider;

    public AccessFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader("Authorization");
            String requestURI = request.getRequestURI() != null ? request.getRequestURI() : "";
            String cleanToken = extractToken(jwtToken);

            boolean requiresAuth = !isLoginEndpoint(requestURI);

            if (requiresAuth && (!hasValidToken(cleanToken))) {
                if(requestURI.equals(ACCEPT_ENDPOINT)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                sendUnauthorizedResponse(response);
            }
            else {
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            sendUnauthorizedResponse(response);
        }
    }

    private String extractToken(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return null;
        }
        return tokenHeader.substring(7).trim();
    }

    private boolean isAuthenticated(final String jwtToken) {
        return jwtTokenProvider.isValid(jwtToken) && !jwtTokenProvider.isTokenExpired(jwtToken);
    }

    private boolean hasValidToken(String token) {
        return token != null && isAuthenticated(token);
    }

    private boolean isLoginEndpoint(String requestURI) {
        return requestURI.equals(String.format("%s/login",USERS_ENDPOINT));
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ErrorBodyInfo errorResponse = new ErrorBodyInfo(
                "Unauthorized. Token not valid",
                HttpStatus.UNAUTHORIZED.value(),
                Instant.now()
        );

        String errorJson = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorJson);
    }
}