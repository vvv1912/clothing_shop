package com.shop.clothing.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ImageFile {
    String message() default "{javax.validation.constraints.ImageFile.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
