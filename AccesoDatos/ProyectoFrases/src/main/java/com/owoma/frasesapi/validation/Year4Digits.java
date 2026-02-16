package com.owoma.frasesapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = Year4DigitsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Year4Digits {
    String message() default "debe ser un anio de 4 cifras (admite negativos)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
