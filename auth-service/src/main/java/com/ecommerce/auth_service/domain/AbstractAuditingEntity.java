package com.ecommerce.auth_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.Instant;

@MappedSuperclass
@JsonIgnoreProperties(value = { "createdBy", "createdDate", "updatedBy", "updatedDate" }, allowGetters = true)
public abstract class AbstractAuditingEntity {

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    public AbstractAuditingEntity() {
    }

    public AbstractAuditingEntity(String createdBy, Instant createdDate, String updatedBy, Instant updatedDate) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    public void prePersist() {
        this.createdBy = "system";
        this.createdDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = "system";
        this.updatedDate = Instant.now();
    }
}