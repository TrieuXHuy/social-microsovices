package com.ecommerce.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class GatewayAccessFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "ecommerce-accounting";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String secret = request.getHeader("X-Gateway-Secret");

        if (secret == null || !secret.equals(SECRET_KEY)) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"Chỉ được truy cập thông qua API Gateway!\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}