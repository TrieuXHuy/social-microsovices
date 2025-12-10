package com.ecommerce.user_service.domain;

import com.ecommerce.user_service.constant.ValidateConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = ValidateConstant.LOGIN_REGEX, message = "Invalid email")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Size(min = 2, max = 50)
    private String username;

    @Size(max = 50)
    @Pattern(regexp = ValidateConstant.PHONE_REGEX, message = "Invalid phone number")
    @Column(unique = true)
    private String phone;

    private String avatarUrl;
    private String bio;

}