package com.qdtas.utility;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NoSpacesValidator.class)
public @interface NoSpaces {
    String message() default "Password cannot contain spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
