package com.ecommerce.shop_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.signerKey}")
    private String jwtKey;

    public String getJwtKey() {
        return jwtKey;
    }
}
