package com.pineframework.core.datamodel.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotSameVersionException extends AbstractException {

    public NotSameVersionException(Object... args) {
        super("error.business.audit.version", 1, args);
    }

}
