package com.shop.clothing.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator  implements ConstraintValidator<ImageFile, MultipartFile> {
    private int maxSizeInKb = 1000;
    @Override
    public void initialize(ImageFile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(MultipartFile s, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
