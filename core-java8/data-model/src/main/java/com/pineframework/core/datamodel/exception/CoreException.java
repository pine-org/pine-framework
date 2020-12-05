package com.pineframework.core.datamodel.exception;


/**
 * All exception convert to {@code CoreException} then send response to the client.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class CoreException extends AbstractException {

    public CoreException(String message, int code, Object... args) {
        super(message, code, args);
    }

}
