package com.pineframework.core.datamodel.exception;

/**
 * When throw a bunch of exception at the same point, also at the same time.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class ExceptionArray extends RuntimeException {

    private final AbstractException[] exceptions;

    private ExceptionArray(AbstractException[] exceptions) {
        this.exceptions = exceptions;
    }

    public static ExceptionArray of(AbstractException[] exceptions) {
        return new ExceptionArray(exceptions);
    }

    public AbstractException[] getExceptions() {
        return exceptions;
    }
}
