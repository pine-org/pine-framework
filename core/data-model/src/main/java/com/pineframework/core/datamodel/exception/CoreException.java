package com.pineframework.core.datamodel.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class CoreException extends AbstractException {

    public CoreException(String message, int code, Object... args) {
        super(message, code, args);
    }

}
