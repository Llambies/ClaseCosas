package com.owoma.frasesapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Year4DigitsValidator implements ConstraintValidator<Year4Digits, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int absoluteValue = Math.abs(value);
        return absoluteValue >= 1000 && absoluteValue <= 9999;
    }
}
