package com.pineframework.core.rest;

import com.pineframework.core.business.exception.ExceptionArray;
import com.pineframework.core.business.exception.ValidationException;
import org.springframework.validation.Errors;

public class ExceptionUtils {

    public static void checkErrors(Errors errors) {
        if (!errors.hasErrors())
            return;

        ValidationException[] validationExceptions = errors.getFieldErrors().stream()
                .map(error -> new ValidationException(error.getDefaultMessage(), error.getField()))
                .toArray(ValidationException[]::new);

        throw ExceptionArray.of(validationExceptions);
    }
}
