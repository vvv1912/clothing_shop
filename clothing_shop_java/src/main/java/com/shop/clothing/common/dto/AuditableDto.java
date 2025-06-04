package com.shop.clothing.common.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AuditableDto {
    //    private String createdBy;
    private java.time.LocalDateTime createdDate;

    public String getCreatedDateDisplay() {
        return createdDate == null ? "" : createdDate.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy"));
    }
//    private String lastModifiedBy;
//    private java.time.LocalDateTime lastModifiedDate;
}
