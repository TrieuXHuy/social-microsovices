package com.ecommerce.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {

    private Long id;
    private String email;
    private String username;
    private String phone;
    private String avatarUrl;
    private String bio;
}