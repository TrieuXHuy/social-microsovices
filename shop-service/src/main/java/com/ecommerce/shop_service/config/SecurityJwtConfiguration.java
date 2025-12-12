package com.ecommerce.shop_service.config;

import com.ecommerce.shop_service.security.JwtProperties;
import com.ecommerce.shop_service.security.SecurityUtils;
import com.nimbusds.jose.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityJwtConfiguration {
    private final JwtProperties jwtProperties;

    public SecurityJwtConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(SecurityUtils.JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                throw new JwtException("Invalid JWT: " + e.getMessage(), e);
            }
        };
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(this.jwtProperties.getJwtKey()).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtils.JWT_ALGORITHM.getName());
    }
}
