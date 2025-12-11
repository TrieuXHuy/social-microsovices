package com.ecommerce.auth_service.dto.user;

import java.time.Instant;

public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String createdBy;
    private Instant createdDate;
    private String updatedBy;
    private Instant updatedDate;

    public UserResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Instant getCreatedDate() { return createdDate; }
    public void setCreatedDate(Instant createdDate) { this.createdDate = createdDate; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Instant getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(Instant updatedDate) { this.updatedDate = updatedDate; }
}