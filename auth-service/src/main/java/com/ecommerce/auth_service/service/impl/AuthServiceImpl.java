package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.domain.User;
import com.ecommerce.auth_service.dto.auth.AuthRequest;
import com.ecommerce.auth_service.dto.auth.AuthResponse;
import com.ecommerce.auth_service.dto.auth.RegisterRequest;
import com.ecommerce.auth_service.security.JwtProperties;
import com.ecommerce.auth_service.security.SecurityUtils;
import com.ecommerce.auth_service.service.AuthService;
import com.ecommerce.auth_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           JwtEncoder jwtEncoder,
                           JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        log.info("Login request for email: {}", request.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Email or Password incorrect"));

        String accessToken = createToken(user.getEmail(), user.getId());

        return new AuthResponse(accessToken);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        log.info("Register request for email: {}", request.getEmail());
        // TODO: Logic register sẽ viết ở đây
        return null;
    }

    public String createToken(String email, Long userId) {

        Instant now = Instant.now();
        Instant validity = now.plus(this.jwtProperties.getTokenExpiration(), ChronoUnit.SECONDS);


        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("userId", userId)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}