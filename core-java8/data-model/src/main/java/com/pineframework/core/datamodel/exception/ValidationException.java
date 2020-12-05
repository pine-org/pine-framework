package com.pineframework.core.datamodel.exception;


/**
 * When model fields are invalid, then throw {@code ValidationException}
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class ValidationException extends AbstractException {

    public ValidationException(String message, Object... args) {
        super(message, 422, args);
    }

}
