package com.pineframework.core.business.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class RequiredException extends AbstractException {

    public RequiredException(String... args) {
        super("error.validation.notNull", 3, args);
    }

}
