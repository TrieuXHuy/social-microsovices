package com.ecommerce.post_service.client;

import com.ecommerce.post_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    // Đường dẫn này phải khớp với API bên User Service mà bạn đã viết
    // Bên kia là: @GetMapping("/api/users/{id}") -> Thì bên này phải y hệt
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}