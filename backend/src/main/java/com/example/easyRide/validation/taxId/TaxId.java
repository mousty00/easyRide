package com.example.easyRide.validation.taxId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = TaxIdValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaxId {
    String message() default "Invalid Tax Id Code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
