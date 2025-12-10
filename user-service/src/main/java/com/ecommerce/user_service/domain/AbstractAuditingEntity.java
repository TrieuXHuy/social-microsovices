package com.ecommerce.user_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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

    @PrePersist
    public void prePersist() {
//        this.createdBy = SecurityUtils.getCurrentUserLogin().orElse("system");
        this.createdBy = "system";
        this.createdDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
//        this.updatedBy = SecurityUtils.getCurrentUserLogin().orElse("system");
        this.updatedBy = "system";
        this.updatedDate = Instant.now();

    }
}