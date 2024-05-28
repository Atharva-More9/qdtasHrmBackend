package com.qdtas.utility;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NonZeroValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonZero {
    String message() default "Department ID cannot be zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
