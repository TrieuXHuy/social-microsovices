package com.ecommerce.auth_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.signerKey}")
    private String jwtKey;

    @Value("${jwt.expiration}")
    private long tokenExpiration;

    public long getTokenExpiration() {
        return tokenExpiration;
    }

    public String getJwtKey() {
        return jwtKey;
    }
}
