package com.pineframework.core.datamodel.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundDataException extends AbstractException {

    public NotFoundDataException(Object... args) {
        super("error.business.notFoundData", 1, args);
    }

}
