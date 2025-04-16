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

        // region validazioni avanzate

//        if (!taxIdCode.matches("[0-9]+")) return false;
//        if (!taxIdCode.matches("[0-9]{13}")) return false;
//        if (!taxIdCode.matches("[0-9]{11}[0-2][0-9]")) return false;
//        if (!taxIdCode.matches("[0-9]{11}[0-2][0-9][0-1][0-9]")) return false;
//        if (!taxIdCode.matches("[0-9]{11}[0-2][0-9][0-1][0-9][0-3][0-9]")) return false;
        // endregion

    }
}
