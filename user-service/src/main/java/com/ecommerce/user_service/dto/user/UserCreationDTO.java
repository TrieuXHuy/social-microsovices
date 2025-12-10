package com.ecommerce.user_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserCreationDTO implements Serializable {

    // Không cần ID vì nó được sinh ra khi tạo

    @NotBlank(message = "Email must not be blank!")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password must not be blank!") // Bắt buộc khi tạo
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @Size(min = 2, max = 50)
    @NotBlank(message = "Username must not be blank!")
    private String username;

    @Size(max = 50)
    private String phone;

    private String avatarUrl;
    private String bio;
}