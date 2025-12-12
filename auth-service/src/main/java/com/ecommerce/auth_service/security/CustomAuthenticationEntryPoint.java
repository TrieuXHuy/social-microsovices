package com.ecommerce.auth_service.security;

import com.ecommerce.auth_service.dto.error.ErrorVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        String path = request.getRequestURI();

        ErrorVM errorVM = new ErrorVM();
        errorVM.setPath(path);
        errorVM.setData(null);

        if (authException instanceof BadCredentialsException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorVM.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorVM.setError("Unauthorized");
            errorVM.setMessage("Tài khoản hoặc mật khẩu không chính xác");
        }
        else if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorVM.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorVM.setError("Unauthorized");
            errorVM.setMessage("Bạn cần đăng nhập để truy cập tài nguyên này (Token thiếu hoặc không hợp lệ)");
        }
        else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorVM.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorVM.setError("Internal Server Error");
            errorVM.setMessage("Lỗi xác thực không xác định: " + authException.getMessage());
        }

        new ObjectMapper().writeValue(response.getOutputStream(), errorVM);
    }
}