package com.example.easyRide.validation.taxId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaxIdValidator
        implements ConstraintValidator<TaxId, String> {

    @Override
    public boolean isValid(String taxIdCode, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Invalid Tax Id Code").addConstraintViolation();
        return !taxIdCode.isBlank() && taxIdCode.length() == 13;

    }
}
