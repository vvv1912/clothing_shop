package com.shop.clothing.common;

import com.shop.clothing.config.CurrentUserService;
import com.shop.clothing.config.ICurrentUserService;
import com.shop.clothing.user.entity.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE, dependencyCheck = true)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AuditingEntityListener implements ConfigurableObject {

    private String getCurrentUserId() {
        var authen = SecurityContextHolder.getContext().getAuthentication();
        if (authen instanceof AnonymousAuthenticationToken) {
            return "system";
        }
        var domainAuthen = (User) authen.getPrincipal();
        return domainAuthen.getUserId();
    }

    @PrePersist
    public void touchForCreate(AuditableEntity target) {

//        target.setCreatedBy(currentUserId);
        target.setCreatedDate(java.time.LocalDateTime.now());
    }

    @PreUpdate
    public void touchForUpdate(AuditableEntity target) {
//        var currentUserId = getCurrentUserId();
//        target.setLastModifiedBy(currentUserId);
//        target.setLastModifiedDate(java.time.LocalDateTime.now());
    }
}
