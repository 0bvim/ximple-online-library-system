package com.ximple.library.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OneOfValidator implements ConstraintValidator<OneOf, Enum<?>> {
    private Enum<?>[] allowedValues;

    @Override
    public void initialize(OneOf annotation) {
        allowedValues = (Enum<?>[]) annotation.getClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(allowedValues).contains(value) ;
    }
}
