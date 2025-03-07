package com.ximple.library.utils;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OneOfValidator.class)
public @interface OneOf {
    String message() default "Invalid value. Must be one of: {allowedValues}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    Class<? extends Enum<?>> value();
}
