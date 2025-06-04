package com.shop.clothing.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.AbstractAuditable;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditableEntity  {
//    @CreatedBy
//    private String createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private java.time.LocalDateTime createdDate;
//    @LastModifiedBy
//    private String lastModifiedBy;
//    @Temporal(TemporalType.TIMESTAMP)
//    private java.time.LocalDateTime lastModifiedDate;
}
