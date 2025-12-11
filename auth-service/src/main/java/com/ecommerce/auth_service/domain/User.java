package com.ecommerce.auth_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Size(min = 2, max = 50)
    private String username;

    public User() {
        super();
    }

    public User(Long id, String email, String password, String username,
                String createdBy, Instant createdDate, String updatedBy, Instant updatedDate) {
        super(createdBy, createdDate, updatedBy, updatedDate);
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password, String username) {
        super();
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}