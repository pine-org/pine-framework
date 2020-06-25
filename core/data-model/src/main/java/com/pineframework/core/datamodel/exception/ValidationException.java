package com.pineframework.core.datamodel.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class ValidationException extends AbstractException {

    public ValidationException(String message, Object... args) {
        super(message, 422, args);
    }

}
