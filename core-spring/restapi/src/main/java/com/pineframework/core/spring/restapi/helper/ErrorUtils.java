package com.pineframework.core.spring.restapi.helper;

import com.pineframework.core.datamodel.exception.ExceptionArray;
import com.pineframework.core.datamodel.exception.ValidationException;
import org.springframework.validation.Errors;

public class ErrorUtils {

    public static void checkErrors(Errors errors) {
        if (!errors.hasErrors())
            return;

        ValidationException[] validationExceptions = errors.getFieldErrors().stream()
                .map(error -> new ValidationException(error.getDefaultMessage(), error.getField()))
                .toArray(ValidationException[]::new);

        throw ExceptionArray.of(validationExceptions);
    }
}
