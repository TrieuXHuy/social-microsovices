package com.ecommerce.user_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    private Long id;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password; // Nếu gửi lên thì update, nếu null thì bỏ qua

    @Size(min = 2, max = 50)
    private String username;

    @Size(max = 50)
    private String phone;

    private String avatarUrl;
    private String bio;

}