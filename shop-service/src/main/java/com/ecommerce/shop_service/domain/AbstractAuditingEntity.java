package com.ecommerce.shop_service.domain;

import jakarta.persistence.*;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractAuditingEntity {

    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @PrePersist
    public void onPrePersist() {
        Instant now = Instant.now();
        this.createdDate = now;
        this.lastModifiedDate = now;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastModifiedDate = Instant.now();
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}