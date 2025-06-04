package com.shop.clothing.auth.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table

public class Permission implements java.io.Serializable {
    @Id
    @Column(name = "normalized_name", length = 50,updatable = false)
    private String normalizedName;

    @Column(name = "display_name", length = 50,nullable = false)
    private String displayName;

    @Column(name = "description", length = 100)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private java.util.List<Role> roles;
}
